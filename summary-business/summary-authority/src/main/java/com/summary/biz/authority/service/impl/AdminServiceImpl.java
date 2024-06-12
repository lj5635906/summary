package com.summary.biz.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.authority.entity.AdminDO;
import com.summary.biz.authority.mapper.AdminMapper;
import com.summary.biz.authority.service.AdminService;
import com.summary.biz.authority.service.MenuService;
import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.enums.UserStatusEnum;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.common.core.constant.GlobalConstant;
import com.summary.common.core.page.PageResult;
import com.summary.common.core.utils.Assert;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.common.core.utils.RegexUtil;
import com.summary.common.core.utils.crypto.DesUtil;
import com.summary.component.repository.utils.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.summary.client.authority.code.AuthorityExceptionCode.*;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDO> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private MenuService menuService;

    @Override
    public Long addAdmin(AddAdminParam param) {
        // 密码参数校验
        Assert.isFalse(RegexUtil.validatePassword(param.getPassword()), PASSWORD_RULE_ERROR);

        // 电话号码已经存在
        AdminDO adminByMobile = getAdminByMobile(param.getMobile());
        Assert.notNull(adminByMobile, ADMIN_MOBILE_EXISTS);
        // 账号已经存在
        AdminDO adminDO = getAdminByUsername(param.getUsername());
        Assert.notNull(adminDO, ADMIN_ACCOUNT_EXISTS);

        AdminDO admin = ConvertUtils.convert(param, AdminDO.class);
        admin.setPassword(DesUtil.encrypt(param.getPassword()));
        adminMapper.insert(admin);

        return admin.getAdminId();
    }

    @Override
    public void updateAdmin(ModifyAdminParam param) {
        // 密码参数校验
        if (null != param.getPassword()) {
            Assert.isFalse(RegexUtil.validatePassword(param.getPassword()), PASSWORD_RULE_ERROR);
        }

        AdminDO admin = getAdminByAdminId(param.getAdminId());
        Assert.isNull(admin, ADMIN_NON_EXIT);

        // 电话号码已经存在
        AdminDO adminByMobile = getAdminByMobile(param.getMobile());
        if (adminByMobile != null) {
            Assert.isTrue(!admin.getAdminId().equals(adminByMobile.getAdminId()), ADMIN_MOBILE_EXISTS);
        }

        // 账号已经存在
        AdminDO adminDO = getAdminByUsername(param.getUsername());
        if (adminDO != null) {
            Assert.isTrue(!admin.getAdminId().equals(adminDO.getAdminId()), ADMIN_ACCOUNT_EXISTS);
        }

        AdminDO modify = ConvertUtils.convert(param, AdminDO.class);
        if (UserStatusEnum.normal.getCode().equals(modify.getUserStatus())) {
            // 清理掉用户锁定时间
            modify.setLockedTime(null);
        }
        adminMapper.updateById(modify);

    }

    @Override
    public PageResult<AdminDTO> getAdmins(String username, String mobile, Integer userStatus, String realName, Integer pageNum, Integer pageSize) {
        Page<AdminDO> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(username), AdminDO::getUsername, username)
                .eq(StringUtils.isNotBlank(mobile), AdminDO::getMobile, mobile)
                .eq(null != userStatus, AdminDO::getUserStatus, userStatus)
                .eq(StringUtils.isNotBlank(realName), AdminDO::getRealName, realName);
        Page<AdminDO> doPage = adminMapper.selectPage(page, queryWrapper);
        return PageUtils.convertPageResult(doPage, AdminDTO.class);
    }

    @Override
    public List<MenuTreeDTO> getAdminHasMenu(Long adminId) {
        List<MenuTreeDTO> adminMenus = adminMapper.getAdminMenuByAdminId(adminId);
        if (!CollectionUtils.isEmpty(adminMenus)) {
            // 获取根节点数据
            List<MenuTreeDTO> parentMenus = adminMenus.stream()
                    .filter(menu -> (null != menu.getParentId() && GlobalConstant.MenuConstant.ROOT_ID.equals(menu.getParentId())))
                    .sorted(Comparator.comparing(MenuTreeDTO::getSort))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(parentMenus)) {
                return new ArrayList<>();
            }

            // 封装数据
            return menuService.findMenuTree(adminMenus, parentMenus, null);
        }
        return new ArrayList<>();
    }

    @Override
    public AdminDO getAdminByAdminId(Long adminId) {
        return adminMapper.selectById(adminId);
    }

    @Override
    public AdminDO getAdminByMobile(String mobile) {
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminDO::getMobile, mobile);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public AdminDO getAdminByUsername(String username) {
        QueryWrapper<AdminDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminDO::getUsername, username);
        return adminMapper.selectOne(queryWrapper);
    }

}
