package com.summary.biz.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.order.entity.OrderItemDO;
import com.summary.biz.order.mapper.OrderItemMapper;
import com.summary.biz.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 订单-类目表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItemDO> implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItemDO> getOrderItems(Long orderId) {
        QueryWrapper<OrderItemDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderItemDO::getOrderId, orderId);
        return orderItemMapper.selectList(queryWrapper);
    }
}
