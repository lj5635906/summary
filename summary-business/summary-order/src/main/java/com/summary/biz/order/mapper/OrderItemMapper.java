package com.summary.biz.order.mapper;

import com.summary.biz.order.entity.OrderItemDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单-类目表 Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItemDO> {

}
