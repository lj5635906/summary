package com.summary.biz.order.service.impl;

import com.summary.biz.order.entity.OrderPaymentDO;
import com.summary.biz.order.mapper.OrderPaymentMapper;
import com.summary.biz.order.service.OrderPaymentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单-支付信息 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-07-22
 */
@Service
public class OrderPaymentServiceImpl extends ServiceImpl<OrderPaymentMapper, OrderPaymentDO> implements OrderPaymentService {

}
