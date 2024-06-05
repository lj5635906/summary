package com.summary.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.summary.common.core.dto.R;
import com.summary.gateway.config.BlackListProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.summary.common.core.exception.code.BaseExceptionCode.BLACKLISTED;

/**
 * 黑名单过滤器
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Slf4j
@Component
public class BlackListFilter implements GlobalFilter, Ordered {

    @Autowired
    private BlackListProperties blackListProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 客户端IP
        String clientIp = request.getRemoteAddress().getHostString();

        Set<String> blackList = blackListProperties.getPaths();
        if (CollectionUtils.isEmpty(blackList)) {
            return chain.filter(exchange);
        }

        boolean contains = blackList.contains(clientIp);
        if (contains) {
            log.error("客户端IP: {} 已经被加入黑名单", clientIp);
            response.setStatusCode(HttpStatus.OK);
            String data = JSONUtil.parse(R.custom(BLACKLISTED.getCode(), BLACKLISTED.getMessage())).toString();
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        return chain.filter(exchange);
    }

    /**
     * 当前过滤器的顺序(优先级)，数值越⼩，优先级越⾼
     *
     * @return .
     */
    @Override
    public int getOrder() {
        return 100;
    }
}
