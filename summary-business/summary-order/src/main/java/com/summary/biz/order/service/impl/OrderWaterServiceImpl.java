package com.summary.biz.order.service.impl;

import com.summary.biz.order.entity.OrderWaterDO;
import com.summary.biz.order.mapper.OrderWaterMapper;
import com.summary.biz.order.service.OrderWaterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.order.enums.OrderStateEnum;
import com.summary.component.generator.id.snowflake.IdWorker;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单流水 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class OrderWaterServiceImpl extends ServiceImpl<OrderWaterMapper, OrderWaterDO> implements OrderWaterService {

    @Override
    public void saveCreateOrderWater(Long orderId) {
        OrderWaterDO waterDO = OrderWaterDO.builder()
                .waterId(IdWorker.nextId())
                .orderId(orderId)
                .toOrderState(OrderStateEnum.WAIT_PAY.getCode())
                .toOrderStateDesc(OrderStateEnum.WAIT_PAY.getMessage())
                .remark("创建订单")
                .build();
        this.save(waterDO);
    }

    @Override
    public void saveOrderTimeoutCancelWater(Long orderId) {
        OrderWaterDO waterDO = OrderWaterDO.builder()
                .waterId(IdWorker.nextId())
                .orderId(orderId)
                .fromOrderState(OrderStateEnum.WAIT_PAY.getCode())
                .fromOrderStateDesc(OrderStateEnum.WAIT_PAY.getMessage())
                .toOrderState(OrderStateEnum.TIME_OUT_CANCEL.getCode())
                .toOrderStateDesc(OrderStateEnum.TIME_OUT_CANCEL.getMessage())
                .remark("订单超时未支付，取消订单")
                .build();
        this.save(waterDO);
    }
}
