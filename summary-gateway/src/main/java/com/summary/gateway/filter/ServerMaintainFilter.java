package com.summary.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.summary.common.core.dto.R;
import com.summary.gateway.config.ServerMaintainConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.summary.common.core.exception.code.BaseExceptionCode.SERVER_MAINTAIN;

/**
 * 服务器维护
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Component
public class ServerMaintainFilter implements GlobalFilter, Ordered {
    @Autowired
    private ServerMaintainConfig serverMaintainConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        boolean openFlag = serverMaintainConfig.getOpenFlag();
        if (openFlag) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            String data = JSONUtil.parse(R.custom(SERVER_MAINTAIN.getCode(), SERVER_MAINTAIN.getMessage())).toString();
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
