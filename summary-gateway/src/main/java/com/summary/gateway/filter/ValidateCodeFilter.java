package com.summary.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.summary.common.core.dto.R;
import com.summary.common.core.exception.CustomException;
import com.summary.gateway.config.ValidateCodeProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.summary.common.core.constant.GlobalConstant.StrConstant.VALIDATE_CODE_PREFIX;
import static com.summary.common.core.exception.code.BaseExceptionCode.CODE_EXPIRE;
import static com.summary.common.core.exception.code.BaseExceptionCode.CODE_NOT_MATCH;

/**
 * @author jie.luo
 * @since 2024/6/6
 */
@Slf4j
@Component
public class ValidateCodeFilter implements GlobalFilter, Ordered {

    @Autowired
    private ValidateCodeProperties validateCodeProperties;
    /**
     * 验证请求URL与配置的URL是否匹配的工具类
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 需要验证验证码的 url集合
        List<String> paths = validateCodeProperties.getPaths();
        if (CollectionUtils.isEmpty(paths)) {
            return chain.filter(exchange);
        }

        // 当前请求接口
        String uri = exchange.getRequest().getURI().getPath();

        // 是否匹配
        boolean match = false;

        for (String check : paths) {
            match = antPathMatcher.match(check, uri);
            // 当前接口不需要验证权限
            if (match) {
                break;
            }
        }

        // 当前请求地址不需要校验码
        if (!match) {
            return chain.filter(exchange);
        }

        HttpHeaders headers = exchange.getRequest().getHeaders();

        // 返回字符串
        String responseStr = "";

        try {
            String mobile = headers.getFirst("mobile");
            String type = headers.getFirst("type");
            String code = headers.getFirst("code");

            doValidate(mobile, type, code);
            return chain.filter(exchange);
        } catch (CustomException e) {
            responseStr = JSONUtil.toJsonStr(R.custom(e.getCode(), e.getMessage()));
        }

        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.OK);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        byte[] response = responseStr
                .getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    /**
     * 验证码校验逻辑
     *
     * @param mobile 随机数或电话号码
     * @param type   验证码类型
     * @param code   验证码
     */
    public void doValidate(String mobile, String type, String code) {

        if (StringUtils.isBlank(code)) {
            // 验证码为空或已过期
            throw new CustomException(CODE_EXPIRE);
        }

        Object objectCode = redisTemplate.opsForValue().get(VALIDATE_CODE_PREFIX + mobile + "_" + type);

        if (null == objectCode) {
            // 验证码为空或已过期
            throw new CustomException(CODE_EXPIRE);
        }

        String cacheCode = (String) objectCode;

        if (!StringUtils.equals(cacheCode, code)) {
            // 验证码不匹配
            throw new CustomException(CODE_NOT_MATCH);
        }

        // 验证通过后删除验证码
        redisTemplate.delete(VALIDATE_CODE_PREFIX + mobile + "_" + type);
    }

    @Override
    public int getOrder() {
        return 400;
    }
}
