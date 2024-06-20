package com.summary.biz.order.provider;

import com.summary.biz.order.service.OrderService;
import com.summary.client.order.param.CreateOrderParam;
import com.summary.client.remote.OrderRemoteService;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@DubboService
public class OrderRemoteServiceProvider implements OrderRemoteService {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     *
     * @param param {@link CreateOrderParam}
     * @return orderId
     */
    @Override
    public Long createOrder(@Valid CreateOrderParam param) {
        return orderService.createOrder(param);
    }
}
