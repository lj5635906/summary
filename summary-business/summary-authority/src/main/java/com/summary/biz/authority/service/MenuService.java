package com.summary.biz.authority.service;

import com.summary.biz.authority.entity.MenuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
public interface MenuService extends IService<MenuDO> {

    /**
     * 添加资源
     *
     * @param param AddMenuParam
     * @return menuId
     */
    Long addMenuData(AddMenuParam param);

    /**
     * 修改资源
     *
     * @param param ModifyMenuParam
     */
    void modifyMenuData(ModifyMenuParam param);

    /**
     * 删除资源
     *
     * @param param DeleteMenuParam
     */
    void deleteMenuData(DeleteMenuParam param);

    /**
     * 获取资源树
     *
     * @return 获取公司所有资源信息并封装成树状结构返回
     */
    List<MenuTreeDTO> getMenuTree();

    /**
     * 获取角色已经配置的菜单树
     *
     * @param roleId 角色Id
     * @return 获取公司所有资源信息并封装成树状结构返回
     */
    List<MenuTreeDTO> roleHasMenuTree(Long roleId);

    /**
     * 递归处理子集
     *
     * @param allMenus         所有菜单
     * @param parentMenus      父节点集合
     * @param roleCheckedMenus 已经分配的菜单集合
     * @return 菜单树
     */
    List<MenuTreeDTO> findMenuTree(List<MenuTreeDTO> allMenus, List<MenuTreeDTO> parentMenus, Set<Long> roleCheckedMenus);
}
