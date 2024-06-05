package com.summary.gateway.filter;

import com.summary.common.core.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * 接口执行时间filter
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Slf4j
@Component
public class RequestTimeFilter implements GlobalFilter, Ordered {

    /**
     * 请求开始时间戳
     */
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";
    /**
     * 请求开始时间
     */
    private static final String REQUEST_DATE_TIME_BEGIN = "requestDateTimeBegin";
    private static final long SLOW_TIME = 5000;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());
        LocalDateTime start = DateTimeUtils.getNow();
        exchange.getAttributes().put(REQUEST_DATE_TIME_BEGIN, start);

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
            LocalDateTime startDateTime = exchange.getAttribute(REQUEST_DATE_TIME_BEGIN);
            if (startTime != null) {
                long actionTime = System.currentTimeMillis() - startTime;
                if (actionTime > SLOW_TIME) {
                    log.debug(exchange.getRequest().getMethod() + "  " + exchange.getRequest().getURI().getRawPath() + " 执行时间 : " + actionTime + " ms" + "   【" + startDateTime + "】-【" + DateTimeUtils.getNow() + "】 ");
                }
            }
        }));
    }

    /**
     * 当前过滤器的顺序(优先级)，数值越⼩，优先级越⾼
     *
     * @return .
     */
    @Override
    public int getOrder() {
        return 500;
    }
}
