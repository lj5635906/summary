package com.summary.gateway.filter;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.summary.common.core.dto.R;
import com.summary.common.core.exception.code.BaseExceptionCode;
import com.summary.common.core.jwt.JwtUtils;
import com.summary.gateway.config.AuthenticationAnonProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Consumer;

import static com.summary.common.core.constant.GlobalConstant.StrConstant.HEADER_ACCESS_TOKEN_NAME;
import static com.summary.common.core.constant.GlobalConstant.TokenConstant.X_REQUEST_IP;


/**
 * 鉴权过滤器
 *
 * @author jie.luo
 * @since 2024/6/6
 */
@Slf4j
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private AuthenticationAnonProperties authenticationAnonProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // 客户端IP
        String clientIp = request.getRemoteAddress().getHostString();

        // 当前请求接口
        String uri = request.getURI().getPath();
        // 所有不验证token的接口集合
        Set<String> urls = authenticationAnonProperties.getPaths();

        if (CollUtil.isEmpty(urls)) {
            return chain.filter(buildIpExchange(clientIp, exchange));
        }

        for (String anon : urls) {
            boolean match = ANT_PATH_MATCHER.match(anon, uri);
            // 当前接口不需要验证权限
            if (match) {
                return chain.filter(buildIpExchange(clientIp, exchange));
            }
        }

        // 从请求头中取出token
        String accessToken = request.getHeaders().getFirst(HEADER_ACCESS_TOKEN_NAME);

        if (StringUtils.isBlank(accessToken)) {
            return response(exchange, BaseExceptionCode.FORBIDDEN);
        }

        // 校验access_token
        boolean verify = JwtUtils.verify(accessToken);
        if (!verify) {
            return response(exchange, BaseExceptionCode.FORBIDDEN);
        }

        return chain.filter(buildIpExchange(clientIp, exchange));
    }

    /**
     * 构建一个 ServerWebExchange，放入用户信息、请求ip
     */
    private ServerWebExchange buildUserAndIpExchange(String accessToken, String clientIp, ServerWebExchange exchange) {

        Consumer<HttpHeaders> httpHeaders = httpHeader -> {
            httpHeader.set(HEADER_ACCESS_TOKEN_NAME, accessToken);
            httpHeader.set(X_REQUEST_IP, clientIp);
        };
        ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders).build();
        // 将现在的request 变成 change对象
        return exchange.mutate().request(host).build();
    }

    /**
     * 构建一个 ServerWebExchange，放入请求ip
     */
    private ServerWebExchange buildIpExchange(String clientIp, ServerWebExchange exchange) {
        Consumer<HttpHeaders> httpHeaders = httpHeader -> httpHeader.set(X_REQUEST_IP, clientIp);
        ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders).build();
        // 将现在的request 变成 change对象
        return exchange.mutate().request(host).build();
    }

    public static Mono<Void> response(ServerWebExchange exchange, BaseExceptionCode exceptionCode) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.OK);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        String data = JSONUtil.parse(R.custom(exceptionCode)).toString();
        byte[] response = data.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return 300;
    }

}
