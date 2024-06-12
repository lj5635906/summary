package com.summary.client.remote;

import com.summary.client.order.param.CreateOrderParam;
import jakarta.validation.Valid;

/**
 * 订单相关参数
 *
 * @author jie.luo
 * @since 2024/5/31
 */
public interface OrderRemoteService {

    /**
     * 创建订单
     *
     * @param param {@link CreateOrderParam}
     * @return orderId
     */
    Long createOrder(@Valid CreateOrderParam param);
}
