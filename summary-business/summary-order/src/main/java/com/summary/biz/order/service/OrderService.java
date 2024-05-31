package com.summary.biz.order.service;

import com.summary.biz.order.entity.OrderDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.order.param.CreateOrderParam;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface OrderService extends IService<OrderDO> {

    /**
     * 创建订单
     *
     * @param param .
     * @return orderId
     */
    Long createOrder(CreateOrderParam param);
}
