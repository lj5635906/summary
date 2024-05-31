package com.summary.biz.customer.mapper;

import com.summary.biz.customer.entity.CustomerDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
@Mapper
public interface CustomerMapper extends BaseMapper<CustomerDO> {

}
