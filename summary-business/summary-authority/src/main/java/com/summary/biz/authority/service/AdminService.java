package com.summary.biz.authority.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.authority.entity.AdminDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.common.core.page.PageResult;

import java.util.List;

/**
 * <p>
 * 管理员表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
public interface AdminService extends IService<AdminDO> {

    /**
     * 添加管理员
     *
     * @param param AddAdminParam
     * @return 管理员id
     */
    Long addAdmin(AddAdminParam param);

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     */
    void updateAdmin(ModifyAdminParam param);

    /**
     * 获取管理员列表
     *
     * @param username   用户名
     * @param mobile     电话
     * @param userStatus 用户状态: 1-锁定、2-正常 、3-注销
     * @param realName   真实姓名
     * @param pageNum    页码
     * @param pageSize   页条数
     * @return PageInfo<AdminListDTO>
     */
    PageResult<AdminDTO> getAdmins(String username, String mobile, Integer userStatus, String realName, Integer pageNum, Integer pageSize);

    /**
     * 获取管理员
     *
     * @param adminId 管理员id
     * @return 已有菜单集合
     */
    List<MenuTreeDTO> getAdminHasMenu(Long adminId);

    /**
     * 根据 adminId 获取admin
     *
     * @param adminId 管理员id
     * @return AdminDO
     */
    AdminDO getAdminByAdminId(Long adminId);

    /**
     * 根据 mobile 获取admin
     *
     * @param mobile 手机号
     * @return AdminDO
     */
    AdminDO getAdminByMobile(String mobile);

    /**
     * 根据 username 获取admin
     *
     * @param username 用户名
     * @return AdminDO
     */
    AdminDO getAdminByUsername(String username);

}
