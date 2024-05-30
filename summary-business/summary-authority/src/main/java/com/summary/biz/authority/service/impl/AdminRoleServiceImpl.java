package com.summary.biz.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.authority.entity.AdminRoleDO;
import com.summary.biz.authority.entity.RoleDO;
import com.summary.biz.authority.mapper.AdminRoleMapper;
import com.summary.biz.authority.service.AdminRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.authority.service.RoleService;
import com.summary.client.authority.dto.AdminRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 管理员与角色关联表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRoleDO> implements AdminRoleService {


    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public List<AdminRoleDTO> getAdminRole(Long adminId) {

        List<AdminRoleDTO> adminRoles = new ArrayList<>();

        // 所有角色
        List<RoleDO> allRoles = roleService.getAllRoles();
        if (CollectionUtils.isEmpty(allRoles)) {
            return adminRoles;
        }

        // 用户有的角色id
        QueryWrapper<AdminRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AdminRoleDO::getAdminId, adminId);
        List<AdminRoleDO> adminHasRoles = adminRoleMapper.selectList(queryWrapper);
        Set<Long> adminHasRoleIds = adminHasRoles.stream().map(AdminRoleDO::getRoleId).collect(Collectors.toSet());

        allRoles.forEach(roleDO -> {
            adminRoles.add(AdminRoleDTO.builder()
                    .roleId(roleDO.getRoleId())
                    .roleName(roleDO.getRoleName())
                    .checked(adminHasRoleIds.contains(roleDO.getRoleId()))
                    .build());
        });

        return adminRoles;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateAdminRole(Long adminId, List<Long> roleIds) {
        QueryWrapper<AdminRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(AdminRoleDO::getAdminId, adminId);
        adminRoleMapper.delete(queryWrapper);

        if (CollectionUtils.isEmpty(roleIds)) {
            return;
        }

        for (Long roleId : roleIds) {
            adminRoleMapper.insert(AdminRoleDO.builder()
                    .adminId(adminId)
                    .roleId(roleId)
                    .build());
        }
    }
}
