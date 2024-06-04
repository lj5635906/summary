package com.summary.biz.order.service;

import com.summary.biz.order.entity.OrderItemDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单-类目表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface OrderItemService extends IService<OrderItemDO> {

    /**
     * 获取订单明细
     *
     * @param orderId 订单号
     * @return 订单明细
     */
    List<OrderItemDO> getOrderItems(Long orderId);
}
