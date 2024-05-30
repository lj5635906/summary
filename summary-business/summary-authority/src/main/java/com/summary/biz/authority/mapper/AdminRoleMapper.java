package com.summary.biz.authority.mapper;

import com.summary.biz.authority.entity.AdminRoleDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 管理员与角色关联表 Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Mapper
public interface AdminRoleMapper extends BaseMapper<AdminRoleDO> {

}
