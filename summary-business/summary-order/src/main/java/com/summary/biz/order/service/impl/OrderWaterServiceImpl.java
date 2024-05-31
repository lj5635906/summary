package com.summary.biz.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.summary.biz.order.entity.OrderWaterDO;
import com.summary.biz.order.mapper.OrderWaterMapper;
import com.summary.biz.order.service.OrderWaterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.order.enums.OrderStateEnum;
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
                .waterId(IdWorker.getId())
                .orderId(orderId)
                .toOrderState(OrderStateEnum.WAIT_PAY.getCode())
                .toOrderStateDesc(OrderStateEnum.WAIT_PAY.getMessage())
                .build();
        this.save(waterDO);
    }
}
