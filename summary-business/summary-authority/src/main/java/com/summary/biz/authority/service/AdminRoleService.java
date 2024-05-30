package com.summary.biz.authority.service;

import com.summary.biz.authority.entity.AdminRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.authority.dto.AdminRoleDTO;

import java.util.List;

/**
 * <p>
 * 管理员与角色关联表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
public interface AdminRoleService extends IService<AdminRoleDO> {

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    List<AdminRoleDTO> getAdminRole(Long adminId);

    /**
     * 修改管理员角色信息
     *
     * @param adminId   adminId
     * @param roleIds 角色id集合
     */
    void updateAdminRole(Long adminId, List<Long> roleIds);
}
