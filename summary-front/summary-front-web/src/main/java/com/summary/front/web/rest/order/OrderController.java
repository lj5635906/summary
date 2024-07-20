package com.summary.front.web.rest.order;

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
 * 订单相关接口
 *
 * @author jie.luo
 * @since 2024/6/25
 */
@RestController
@RequestMapping("/web/order")
public class OrderController {

    @Autowired
    private OrderRemoteService orderRemoteService;

    /**
     * 创建订单
     *
     * @param param 创建订单参数
     * @return 订单id
     */
    @PostMapping("/createOrder")
    public R<Long> createOrder(@Valid @RequestBody CreateOrderParam param) {
        return R.success(orderRemoteService.createOrder(param));
    }

}
