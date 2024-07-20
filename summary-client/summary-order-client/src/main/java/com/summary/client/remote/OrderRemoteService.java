package com.summary.client.remote;

import com.summary.client.order.param.CreateOrderParam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 订单相关参数
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@FeignClient(value = "summary-order", path = "/order")
public interface OrderRemoteService {

    /**
     * 创建订单
     *
     * @param param {@link CreateOrderParam}
     * @return orderId
     */
    @PostMapping("createOrder")
    Long createOrder(@Valid @RequestBody CreateOrderParam param);
}
