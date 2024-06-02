package com.summary.biz.order.rest;

import com.summary.biz.order.service.OrderService;
import com.summary.client.order.param.CreateOrderParam;
import com.summary.client.remote.OrderRemoteService;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/order")
public class OrderController implements OrderRemoteService {

    @Autowired
    private OrderService orderService;
    
    /**
     * 创建订单
     *
     * @param param {@link CreateOrderParam}
     * @return orderId
     */
    @Override
    @PostMapping("/createOrder")
    public R<Long> createOrder(@Valid @RequestBody CreateOrderParam param) {
        return R.success(orderService.createOrder(param));
    }
}
