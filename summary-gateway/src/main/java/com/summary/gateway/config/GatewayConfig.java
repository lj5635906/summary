package com.summary.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * 网关相关配置
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Configuration
public class GatewayConfig {

    /**
     * 指定ip限流的key
     *
     * @return KeyResolver
     */
    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getHostName());
    }

}
