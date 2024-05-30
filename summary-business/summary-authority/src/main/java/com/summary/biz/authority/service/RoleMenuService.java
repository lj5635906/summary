package com.summary.biz.authority.service;

import com.summary.biz.authority.entity.RoleMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.param.RoleCheckMenuParam;

import java.util.Set;

/**
 * <p>
 * 角色菜单关联表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
public interface RoleMenuService extends IService<RoleMenuDO> {

    /**
     * 角色分配菜单
     *
     * @param param RoleCheckMenuParam
     */
    void roleSetMenu(RoleCheckMenuParam param);

    /**
     * 获取角色已配置的资源id
     *
     * @param roleId 角色id
     * @param type   类型:0-全选、1-半选
     * @return 资源数量
     */
    Set<Long> getRoleHasMenus(Long roleId, Integer type);

    /**
     * 获取角色已配置的资源id
     *
     * @param roleId 角色id
     * @return 资源数量
     */
    RoleCheckedMenuDTO getRoleHasMenus(Long roleId);
}
