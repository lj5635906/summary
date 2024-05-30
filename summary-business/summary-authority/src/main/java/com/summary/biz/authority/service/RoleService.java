package com.summary.biz.authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.biz.authority.entity.RoleDO;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.common.core.page.PageResult;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 获取所有角色
     *
     * @return List<RoleDO>
     */
    List<RoleDO> getAllRoles();

    /**
     * 新增角色
     *
     * @param param AddRoleParam
     * @return roleId
     */
    Long addRole(AddRoleParam param);

    /**
     * 修改一条角色
     *
     * @param param UpdateRoleParam
     */
    void updateRole(UpdateRoleParam param);

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return PageResult<RoleVo>
     */
    PageResult<RoleDTO> getRoles(String roleName, Integer pageNum, Integer pageSize);
}
