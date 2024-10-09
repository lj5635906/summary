package com.summary.biz.order.mapper;

import com.summary.biz.order.entity.OrderPaymentDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单-支付信息 Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-07-22
 */
@Mapper
public interface OrderPaymentMapper extends BaseMapper<OrderPaymentDO> {

}
