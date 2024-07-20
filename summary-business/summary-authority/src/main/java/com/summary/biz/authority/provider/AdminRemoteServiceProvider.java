package com.summary.biz.authority.provider;

import com.summary.biz.authority.entity.AdminDO;
import com.summary.biz.authority.service.AdminRoleService;
import com.summary.biz.authority.service.AdminService;
import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.AdminRoleDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.client.authority.param.ModifyAdminRoleParam;
import com.summary.client.remote.AdminRemoteService;
import com.summary.common.core.page.PageResult;
import com.summary.common.core.utils.ConvertUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@RestController
@RequestMapping("/authority/admin")
public class AdminRemoteServiceProvider implements AdminRemoteService {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 添加管理员
     *
     * @param param AddAdminParam
     * @return yes or no
     */
    @Override
    @PostMapping("/addAdmin")
    public Long addAdmin(@Valid @RequestBody AddAdminParam param) {
        return adminService.addAdmin(param);
    }

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @Override
    @PutMapping("/updateAdmin")
    public Boolean updateAdmin(@Valid @RequestBody ModifyAdminParam param) {
        adminService.updateAdmin(param);
        return true;
    }

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
    @Override
    @GetMapping("/page")
    public PageResult<AdminDTO> page(@RequestParam(required = false, name = "username") String username,
                                     @RequestParam(required = false, name = "mobile") String mobile,
                                     @RequestParam(required = false, name = "userStatus") Integer userStatus,
                                     @RequestParam(required = false, name = "realName") String realName,
                                     @RequestParam(name = "pageNum") Integer pageNum,
                                     @RequestParam(name = "pageSize") Integer pageSize) {
        return adminService.getAdmins(username, mobile, userStatus, realName, pageNum, pageSize);
    }

    /**
     * 根据用户名获取管理员
     *
     * @param username 用户名
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByUsername")
    public AdminDTO getAdminByUsername(@RequestParam(name = "username") String username) {
        AdminDO admin = adminService.getAdminByUsername(username);
        return ConvertUtils.convert(admin, AdminDTO.class);
    }

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId adminId
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByAdminId")
    public AdminDTO getAdminByAdminId(@RequestParam(name = "adminId") Long adminId) {
        AdminDO admin = adminService.getAdminByAdminId(adminId);
        return ConvertUtils.convert(admin, AdminDTO.class);
    }

    /**
     * 根据手机号获取管理员
     *
     * @param mobile 手机号
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByMobile")
    public AdminDTO getAdminByMobile(@RequestParam(name = "mobile") String mobile) {
        AdminDO admin = adminService.getAdminByMobile(mobile);
        return ConvertUtils.convert(admin, AdminDTO.class);
    }

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    @Override
    @GetMapping("getAdminRoles")
    public List<AdminRoleDTO> getAdminRoles(@RequestParam(name = "adminId") Long adminId) {
        return adminRoleService.getAdminRole(adminId);
    }

    /**
     * 修改管理员角色信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @Override
    @PutMapping("updateAdminRole")
    public Boolean updateAdminRole(@Valid @RequestBody ModifyAdminRoleParam param) {
        adminRoleService.updateAdminRole(param.getAdminId(), param.getRoleIds());
        return true;
    }

    /**
     * 获取管理员已有权限菜单
     *
     * @param adminId 管理员id
     * @return MenuTreeVo
     */
    @Override
    @GetMapping("/admin/hasMenuTree")
    public List<MenuTreeDTO> getAdminHasMenu(@RequestParam(name = "adminId") Long adminId) {
        return adminService.getAdminHasMenu(adminId);
    }
}
