package com.summary.biz.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summary.biz.authority.entity.RoleDO;
import com.summary.biz.authority.mapper.RoleMapper;
import com.summary.biz.authority.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.authority.code.AuthorityExceptionCode;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.common.core.page.PageResult;
import com.summary.common.core.utils.Assert;
import com.summary.component.repository.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.summary.client.authority.code.AuthorityExceptionCode.ROLE_EXISTS;
import static com.summary.client.authority.code.AuthorityExceptionCode.ROLE_NON_EXIT;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleDO> getAllRoles() {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        return roleMapper.selectList(queryWrapper);
    }

    public boolean checkRoleExist(String roleName) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(RoleDO::getRoleName, roleName);
        Long count = roleMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public Long addRole(AddRoleParam param) {

        Assert.isTrue(checkRoleExist(param.getRoleName()), ROLE_EXISTS);

        RoleDO role = new RoleDO();
        role.setRoleName(param.getRoleName());
        role.setRemark(param.getRemark());
        role.setRoleAlias(param.getRoleAlias());
        roleMapper.insert(role);

        return role.getRoleId();
    }

    @Override
    public void updateRole(UpdateRoleParam param) {

        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(RoleDO::getRoleName, param.getRoleName());
        RoleDO count = roleMapper.selectOne(queryWrapper);

        Assert.isTrue(count != null && !count.getRoleId().equals(param.getRoleId()), ROLE_EXISTS);

        // 获取角色
        RoleDO role = roleMapper.selectById(param.getRoleId());
        Assert.isNull(role, ROLE_NON_EXIT);

        role.setRemark(param.getRemark());
        role.setRoleName(param.getRoleName());
        role.setRemark(param.getRemark());
        role.setRoleAlias(param.getRoleAlias());
        roleMapper.updateById(role);
    }

    @Override
    public PageResult<RoleDTO> getRoles(String roleName, Integer pageNum, Integer pageSize) {
        Page<RoleDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(roleName), RoleDO::getRoleName, roleName);
        Page<RoleDO> doPage = roleMapper.selectPage(page, queryWrapper);
        return PageUtils.convertPageResult(doPage, RoleDTO.class);
    }
}
