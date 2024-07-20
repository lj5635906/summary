package com.summary.biz.order.provider;

import com.summary.biz.order.service.OrderService;
import com.summary.client.order.param.CreateOrderParam;
import com.summary.client.remote.OrderRemoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单相关接口
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/order")
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
    @PostMapping("createOrder")
    public Long createOrder(@Valid @RequestBody CreateOrderParam param) {
        return orderService.createOrder(param);
    }
}
