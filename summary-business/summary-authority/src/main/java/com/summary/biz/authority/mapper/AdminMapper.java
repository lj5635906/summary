package com.summary.biz.authority.mapper;

import com.summary.biz.authority.entity.AdminDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.summary.client.authority.dto.MenuTreeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Mapper
public interface AdminMapper extends BaseMapper<AdminDO> {

    /**
     * 获取管理员已有的菜单
     *
     * @param adminId 管理员id
     * @return List<AdminMenuDto>
     */
    List<MenuTreeDTO> getAdminMenuByAdminId(@Param("adminId") Long adminId);
}
