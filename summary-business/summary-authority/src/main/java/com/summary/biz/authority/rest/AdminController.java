package com.summary.biz.authority.rest;

import com.summary.biz.authority.entity.AdminDO;
import com.summary.biz.authority.service.AdminRoleService;
import com.summary.biz.authority.service.AdminService;
import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.AdminRoleDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.client.authority.param.ModifyAdminRoleParam;
import com.summary.client.remote.feign.AdminRemoteService;
import com.summary.common.core.dto.R;
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
public class AdminController implements AdminRemoteService {

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
    public R<Long> addAdmin(@Valid @RequestBody AddAdminParam param) {
        Long adminId = adminService.addAdmin(param);
        return R.success(adminId);
    }

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @Override
    @PutMapping("/updateAdmin")
    public R<Boolean> updateAdmin(@Valid @RequestBody ModifyAdminParam param) {
        adminService.updateAdmin(param);
        return R.success(true);
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
    public R<PageResult<AdminDTO>> page(String username, String mobile, Integer userStatus, String realName, Integer pageNum, Integer pageSize) {
        return R.success(adminService.getAdmins(username, mobile, userStatus, realName, pageNum, pageSize));
    }

    /**
     * 根据用户名获取管理员
     *
     * @param username 用户名
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByUsername")
    public R<AdminDTO> getAdminByUsername(@RequestParam(name = "username") String username) {
        AdminDO admin = adminService.getAdminByUsername(username);
        return R.success(ConvertUtils.convert(admin, AdminDTO.class));
    }

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId adminId
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByAdminId")
    public R<AdminDTO> getAdminByAdminId(@RequestParam(name = "adminId") Long adminId) {
        AdminDO admin = adminService.getAdminByAdminId(adminId);
        return R.success(ConvertUtils.convert(admin, AdminDTO.class));
    }

    /**
     * 根据手机号获取管理员
     *
     * @param mobile 手机号
     * @return AdminVo
     */
    @Override
    @GetMapping("/adminByMobile")
    public R<AdminDTO> getAdminByMobile(@RequestParam(name = "mobile") String mobile) {
        AdminDO admin = adminService.getAdminByMobile(mobile);
        return R.success(ConvertUtils.convert(admin, AdminDTO.class));
    }

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    @Override
    @GetMapping("getAdminRoles")
    public R<List<AdminRoleDTO>> getAdminRoles(@RequestParam(name = "adminId") Long adminId) {
        List<AdminRoleDTO> adminRole = adminRoleService.getAdminRole(adminId);
        return R.success(adminRole);
    }

    /**
     * 修改管理员角色信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @Override
    @PutMapping("updateAdminRole")
    public R<Boolean> updateAdminRole(@Valid @RequestBody ModifyAdminRoleParam param) {
        adminRoleService.updateAdminRole(param.getAdminId(), param.getRoleIds());
        return R.success(true);
    }

    /**
     * 获取管理员已有权限菜单
     *
     * @param adminId 管理员id
     * @return MenuTreeVo
     */
    @Override
    @GetMapping("/admin/hasMenuTree")
    public R<List<MenuTreeDTO>> getAdminHasMenu(@RequestParam(name = "adminId") Long adminId) {
        return R.success(adminService.getAdminHasMenu(adminId));
    }
}
