package com.summary.gateway.handler;

import cn.hutool.json.JSONUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.summary.common.core.dto.R;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;

/**
 * Gateway 全局异常处理
 *
 * @author jie.luo
 * @since 2024/6/6
 */
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders HttpMessageReader
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers ViewResolver
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters HttpMessageWriter
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

//        log.error("网关出现异常 : ", ex);

        // 按照异常类型进行处理
        HttpStatusCode httpStatus;
        R body;
        if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.OK;
            body = R.custom(NOT_FOUND);
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatusCode();
            body = R.custom(UNKNOWN_EXCEPTION.getCode(), responseStatusException.getMessage());
        } else if (ex instanceof ConnectException) {
            // feign.RetryableException: connect timed out executing POST  请求链接超时
            httpStatus = HttpStatus.OK;
            body = R.custom(TIME_OUT);
        } else if (ex instanceof RetryableException) {
            // feign.RetryableException: connect timed out executing POST  请求链接超时
            httpStatus = HttpStatus.OK;
            body = R.custom(TIME_OUT);
        } else if (ex instanceof RuntimeException) {
            String result = ex.getMessage();
            if (StringUtils.isNotBlank(result) && result.contains("Load balancer does not have available server")) {
                // com.netflix.client.ClientException: Load balancer does not have available server for client: developer
                httpStatus = HttpStatus.OK;
                body = R.custom(SENTINEL);
            } else {
                httpStatus = HttpStatus.OK;
                body = R.custom(SERVER_ERROR);
            }

        } else if (ex instanceof BlockException) {
            httpStatus = HttpStatus.OK;
            if (ex instanceof FlowException) {
                // 限流异常
                body = R.custom(FLOW_EXCEPTION);
            } else if (ex instanceof DegradeException) {
                // 熔断异常
                body = R.custom(DEGRADE_EXCEPTION);
            } else if (ex instanceof ParamFlowException) {
                // 热点参数限流
                body = R.custom(PARAM_FLOW_EXCEPTION);
            } else if (ex instanceof SystemBlockException) {
                // 系统规则异常
                body = R.custom(SYSTEM_BLOCK_EXCEPTION);
            } else if (ex instanceof AuthorityException) {
                // 授权规则异常
                body = R.custom(AUTHORITY_EXCEPTION);
            } else {
                body = R.custom(SENTINEL);
            }
        } else {
            httpStatus = HttpStatus.OK;
            body = R.custom(SERVER_ERROR);
        }

        // 封装响应体,此body可修改为自己的jsonBody
        Map<String, Object> result = new HashMap<>(2, 1);
        result.put("httpStatus", httpStatus);
        result.put("body", JSONUtil.toJsonStr(body));

        // 错误记录
        ServerHttpRequest request = exchange.getRequest();
        log.error("[全局异常处理]异常请求路径:{},记录异常信息:{}", request.getPath(), ex.getMessage());

        // 参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        exceptionHandlerResult.set(result);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param request ServerRequest
     * @return Mono<ServerResponse>
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> result = exceptionHandlerResult.get();
        return ServerResponse.status((HttpStatus) result.get("httpStatus"))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(result.get("body")));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange ServerWebExchange
     * @param response ServerResponse
     * @return Mono
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GlobalExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GlobalExceptionHandler.this.viewResolvers;
        }

    }
}
