package com.summary.client.remote;

import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.AdminRoleDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.client.authority.param.ModifyAdminRoleParam;
import com.summary.common.core.page.PageResult;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 管理员相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public interface AdminRemoteService {

    /**
     * 添加管理员
     *
     * @param param AddAdminParam
     * @return yes or no
     */
    Long addAdmin(@Valid AddAdminParam param);

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    Boolean updateAdmin(@Valid ModifyAdminParam param);

    /**
     * 分页获取管理员列表
     *
     * @param username   用户名
     * @param mobile     电话
     * @param userStatus 用户状态: 1-锁定、2-正常 、3-注销
     * @param realName   真实姓名
     * @param pageNum    页码
     * @param pageSize   页条数
     * @return PageInfo<AdminListDTO>
     */
    PageResult<AdminDTO> page(String username,
                              String mobile,
                              Integer userStatus,
                              String realName,
                              Integer pageNum,
                              Integer pageSize);

    /**
     * 根据用户名获取管理员
     *
     * @param username 用户名
     * @return AdminVo
     */
    AdminDTO getAdminByUsername(String username);

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId adminId
     * @return AdminVo
     */
    AdminDTO getAdminByAdminId(Long adminId);

    /**
     * 根据手机号获取管理员
     *
     * @param mobile 手机号
     * @return AdminVo
     */
    AdminDTO getAdminByMobile(String mobile);

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    List<AdminRoleDTO> getAdminRoles(Long adminId);

    /**
     * 修改管理员角色信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    Boolean updateAdminRole(@Valid ModifyAdminRoleParam param);

    /**
     * 获取管理员已有权限菜单
     *
     * @param adminId 管理员id
     * @return MenuTreeVo
     */
    List<MenuTreeDTO> getAdminHasMenu(Long adminId);
}
