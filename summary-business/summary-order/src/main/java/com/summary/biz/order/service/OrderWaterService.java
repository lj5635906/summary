package com.summary.biz.order.service;

import com.summary.biz.order.entity.OrderWaterDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单流水 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface OrderWaterService extends IService<OrderWaterDO> {

    /**
     * 添加下单流水
     *
     * @param orderId 订单号
     */
    void saveCreateOrderWater(Long orderId);
}
