package com.summary.biz.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.authority.entity.RoleMenuDO;
import com.summary.biz.authority.mapper.RoleMenuMapper;
import com.summary.biz.authority.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.param.RoleCheckMenuParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单关联表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDO> implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public void roleSetMenu(RoleCheckMenuParam param) {

        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(RoleMenuDO::getRoleId, param.getRoleId());
        roleMenuMapper.delete(queryWrapper);

        if (!CollectionUtils.isEmpty(param.getChecked())) {
            for (Long menuId : param.getChecked()) {
                roleMenuMapper.insert(RoleMenuDO.builder()
                        .type(0)
                        .menuId(menuId)
                        .roleId(param.getRoleId())
                        .build());
            }
        }

        if (!CollectionUtils.isEmpty(param.getHalfChecked())) {
            for (Long menuId : param.getHalfChecked()) {
                roleMenuMapper.insert(RoleMenuDO.builder()
                        .type(1)
                        .menuId(menuId)
                        .roleId(param.getRoleId())
                        .build());

            }
        }
    }

    @Override
    public Set<Long> getRoleHasMenus(Long roleId, Integer type) {

        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(RoleMenuDO::getRoleId, roleId)
                .eq(null != type, RoleMenuDO::getType, type);
        List<RoleMenuDO> list = roleMenuMapper.selectList(queryWrapper);

        return list.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

    @Override
    public RoleCheckedMenuDTO getRoleHasMenus(Long roleId) {

        RoleCheckedMenuDTO menuDTO = new RoleCheckedMenuDTO();

        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(RoleMenuDO::getRoleId, roleId);
        List<RoleMenuDO> list = roleMenuMapper.selectList(queryWrapper);

        if (!CollectionUtils.isEmpty(list)) {
            for (RoleMenuDO roleMenuDO : list) {
                if (roleMenuDO.getType() == 0) {
                    menuDTO.getChecked().add(roleMenuDO.getMenuId());
                }else {
                    menuDTO.getHalfChecked().add(roleMenuDO.getMenuId());
                }
            }
        }

        return menuDTO;
    }

}
