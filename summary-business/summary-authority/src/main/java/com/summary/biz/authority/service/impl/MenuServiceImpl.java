package com.summary.biz.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.authority.entity.MenuDO;
import com.summary.biz.authority.mapper.MenuMapper;
import com.summary.biz.authority.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.authority.service.RoleMenuService;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.enums.MenuTypeEnum;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import com.summary.common.core.utils.Assert;
import com.summary.common.core.utils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.summary.client.authority.code.AuthorityExceptionCode.MENU_NON_EXIT;
import static com.summary.common.core.constant.GlobalConstant.MenuConstant.ROOT_ID;
import static com.summary.common.core.constant.GlobalConstant.MenuConstant.ROOT_NAME;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RoleMenuService roleMenuService;


    @Override
    public Long addMenuData(AddMenuParam param) {
        MenuDO menu = new MenuDO();
        BeanUtils.copyProperties(param, menu);

        // 获取父节点信息
        Long parentId = param.getParentId();
        if (!ROOT_ID.equals(parentId)) {
            MenuDO parentMenu = menuMapper.selectById(parentId);
            Assert.isNull(parentMenu, MENU_NON_EXIT);

            menu.setParentId(parentId);
            menu.setParentMenuName(parentMenu.getMenuName());
        } else {
            menu.setParentId(ROOT_ID);
            menu.setParentMenuName(ROOT_NAME);
        }

        // 设置顺序
        int count = Math.toIntExact(getChildrenCount(menu.getParentId()));

        // 设置菜单的类型和等级描述字段
        menu.setMenuTypeDesc(MenuTypeEnum.getByCode(menu.getMenuType()).getMessage());

        menu.setSort(count + 1);
        menuMapper.insert(menu);

        return menu.getMenuId();
    }

    /**
     * 获取子节点数量
     *
     * @param parentId 父节点id
     * @return 子节点数量
     */
    public Long getChildrenCount(Long parentId) {
        QueryWrapper<MenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MenuDO::getParentId, parentId);
        return menuMapper.selectCount(queryWrapper);
    }

    @Override
    public void modifyMenuData(ModifyMenuParam param) {

        MenuDO menuDO = menuMapper.selectById(param.getMenuId());
        Assert.isNull(menuDO, MENU_NON_EXIT);

        BeanUtils.copyProperties(param, menuDO);

        // 设置菜单的类型和等级描述字段
        menuDO.setMenuTypeDesc(MenuTypeEnum.getByCode(menuDO.getMenuType()).getMessage());

        menuMapper.updateById(menuDO);
    }

    @Override
    public void deleteMenuData(DeleteMenuParam param) {
        menuMapper.deleteById(param.getMenuId());
    }

    @Override
    public List<MenuTreeDTO> getMenuTree() {
        return roleHasMenuTree(null);
    }

    @Override
    public List<MenuTreeDTO> roleHasMenuTree(Long roleId) {
        // 获取所有的资源数据
        List<MenuDO> menuList = menuMapper.selectList(new QueryWrapper<>());

        List<MenuTreeDTO> allMenus = ConvertUtils.convertList(menuList, MenuTreeDTO.class);

        if (!CollectionUtils.isEmpty(allMenus)) {
            // 获取根节点数据
            List<MenuTreeDTO> parentMenus = allMenus.stream().filter(menu -> (null != menu.getParentId() && ROOT_ID.equals(menu.getParentId()))).sorted(Comparator.comparing(MenuTreeDTO::getSort)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(parentMenus)) {
                return new ArrayList<>();
            }

            if (null != roleId) {
                // 获取角色已配置的所有资源id
                Set<Long> roleCheckedMenus = roleMenuService.getRoleHasMenus(roleId, null);
                // 封装数据
                return findMenuTree(allMenus, parentMenus, roleCheckedMenus);
            } else {
                return findMenuTree(allMenus, parentMenus, null);
            }
        }
        return new ArrayList<>();
    }

    /**
     * 递归处理子集
     *
     * @param allMenus         所有菜单
     * @param parentMenus      父节点集合
     * @param roleCheckedMenus 已经分配的菜单集合
     * @return 菜单树
     */
    @Override
    public List<MenuTreeDTO> findMenuTree(List<MenuTreeDTO> allMenus, List<MenuTreeDTO> parentMenus, Set<Long> roleCheckedMenus) {
        for (MenuTreeDTO menu : parentMenus) {
            // 判断是否需要选中
            if (!CollectionUtils.isEmpty(roleCheckedMenus)) {
                if (roleCheckedMenus.contains(menu.getMenuId())) {
                    menu.setChecked(true);
                }
            }

            // 获取当前节点的所有子节点
            List<MenuTreeDTO> childrenMenus = allMenus.stream().filter(menus -> menu.getMenuId().equals(menus.getParentId())).sorted(Comparator.comparing(MenuTreeDTO::getSort)).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(childrenMenus)) {
                continue;
            }

            if (!CollectionUtils.isEmpty(roleCheckedMenus)) {
                // 判断选中类型：全选、半选
                menu.setCheckedType(0);
                for (MenuTreeDTO childrenMenu : childrenMenus) {
                    if (!roleCheckedMenus.contains(childrenMenu.getMenuId())) {
                        menu.setCheckedType(1);
                        break;
                    }
                }
            }

            // 处理子节点
            menu.setChildren(findMenuTree(allMenus, childrenMenus, roleCheckedMenus));
        }
        return parentMenus;
    }
}
