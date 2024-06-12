package com.summary.client.remote;

import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.RoleCheckMenuParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.common.core.page.PageResult;

import java.util.List;


/**
 * 角色相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public interface RoleRemoteService {

    /**
     * 添加角色
     *
     * @param param AddRoleParam
     * @return yes or no
     */
    Long addRole(AddRoleParam param);

    /**
     * 修改角色
     *
     * @param param UpdateRoleParam
     * @return yes or no
     */
    Boolean updateRole(UpdateRoleParam param);

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return PageInfo<RoleVo>
     */
    PageResult<RoleDTO> page(String roleName, Integer pageNum, Integer pageSize);

    /**
     * 获取菜单树并验证是否已经选中
     *
     * @param roleId 角色id
     * @return List<MenuTreeDTO>
     */
    List<MenuTreeDTO> getRoleHasMenuTree(Long roleId);

    /**
     * 获取角色已分配的菜单
     *
     * @param roleId 角色id
     * @return RoleCheckedMenuDTO
     */
    RoleCheckedMenuDTO getRoleHasMenus(Long roleId);

    /**
     * 角色分配菜单
     *
     * @param param RoleCheckMenuParam
     * @return yes or no
     */
    Boolean roleSetMenu(RoleCheckMenuParam param);

}
