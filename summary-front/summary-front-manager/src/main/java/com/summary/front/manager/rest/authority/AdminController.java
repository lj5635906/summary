package com.summary.front.manager.rest.authority;

import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.AdminRoleDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.client.authority.param.ModifyAdminRoleParam;
import com.summary.client.remote.AdminRemoteService;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import com.summary.component.auth.TokenManager;
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
@RequestMapping("/manager/authority")
public class AdminController {

    @Autowired
    private AdminRemoteService adminRemoteService;

    /**
     * 获取当前登录用户信息
     *
     * @return AdminVo
     */
    @GetMapping("/getCurrentAdmin")
    public R<AdminDTO> getCurrentAdmin() {
        return R.success(adminRemoteService.getAdminByAdminId(TokenManager.getUserId()));
    }

    /**
     * 获取登录用户已有权限菜单
     *
     * @return MenuTreeVo
     */
    @GetMapping("/getCurrentAdmin/hasMenuTree")
    public R<List<MenuTreeDTO>> getAdminHasMenu() {
        return R.success(adminRemoteService.getAdminHasMenu(TokenManager.getUserId()));
    }

    /**
     * 添加管理员
     *
     * @param param AddAdminParam
     * @return yes or no
     */
    @PostMapping("/addAdmin")
    public R<Long> addAdmin(@Valid @RequestBody AddAdminParam param) {
        return R.success(adminRemoteService.addAdmin(param));
    }

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @PutMapping("/updateAdmin")
    public R<Boolean> updateAdmin(@Valid @RequestBody ModifyAdminParam param) {
        return R.success(adminRemoteService.updateAdmin(param));
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
    @GetMapping("/page")
    public R<PageResult<AdminDTO>> page(@RequestParam(name = "username", required = false) String username,
                                        @RequestParam(name = "mobile", required = false) String mobile,
                                        @RequestParam(name = "userStatus", required = false) Integer userStatus,
                                        @RequestParam(name = "realName", required = false) String realName,
                                        @RequestParam(name = "pageNum") Integer pageNum,
                                        @RequestParam(name = "pageSize") Integer pageSize) {
        return R.success(adminRemoteService.page(username, mobile, userStatus, realName, pageNum, pageSize));
    }

    /**
     * 根据用户名获取管理员
     *
     * @param username 用户名
     * @return AdminVo
     */
    @GetMapping("/adminByUsername")
    public R<AdminDTO> getAdminByUsername(@RequestParam(name = "username") String username) {
        return R.success(adminRemoteService.getAdminByUsername(username));
    }

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId adminId
     * @return AdminVo
     */
    @GetMapping("/adminByAdminId")
    public R<AdminDTO> getAdminByAdminId(@RequestParam(name = "adminId") Long adminId) {
        return R.success(adminRemoteService.getAdminByAdminId(adminId));
    }

    /**
     * 根据手机号获取管理员
     *
     * @param mobile 手机号
     * @return AdminVo
     */
    @GetMapping("/adminByMobile")
    public R<AdminDTO> getAdminByMobile(@RequestParam(name = "mobile") String mobile) {
        return R.success(adminRemoteService.getAdminByMobile(mobile));
    }

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    @GetMapping("getAdminRoles")
    public R<List<AdminRoleDTO>> getAdminRoles(@RequestParam(name = "adminId") Long adminId) {
        return R.success(adminRemoteService.getAdminRoles(adminId));
    }

    /**
     * 修改管理员角色信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @PutMapping("updateAdminRole")
    public R<Boolean> updateAdminRole(@Valid @RequestBody ModifyAdminRoleParam param) {
        return R.success(adminRemoteService.updateAdminRole(param));
    }
}
