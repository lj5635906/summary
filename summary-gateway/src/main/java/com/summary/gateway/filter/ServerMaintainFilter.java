package com.summary.gateway.filter;

import com.summary.gateway.config.ServerMaintainConfig;
import com.summary.gateway.util.FilterRequestResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.summary.common.core.exception.code.BaseExceptionCode.SERVER_MAINTENANCE;

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
//            return FilterRequestResponseUtil.response(exchange, SERVER_MAINTENANCE);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
