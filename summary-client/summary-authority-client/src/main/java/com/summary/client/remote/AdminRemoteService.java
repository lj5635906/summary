package com.summary.client.remote;

import com.summary.client.authority.dto.AdminDTO;
import com.summary.client.authority.dto.AdminRoleDTO;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddAdminParam;
import com.summary.client.authority.param.ModifyAdminParam;
import com.summary.client.authority.param.ModifyAdminRoleParam;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@FeignClient(name = "summary-authority", path = "/authority/admin")
public interface AdminRemoteService {

    /**
     * 添加管理员
     *
     * @param param AddAdminParam
     * @return yes or no
     */
    @PostMapping("/addAdmin")
    R<Long> addAdmin(@Valid @RequestBody AddAdminParam param);

    /**
     * 修改管理员信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @PutMapping("/updateAdmin")
    R<Boolean> updateAdmin(@Valid @RequestBody ModifyAdminParam param);

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
    R<PageResult<AdminDTO>> page(@RequestParam(required = false, name = "username") String username,
                                 @RequestParam(required = false, name = "mobile") String mobile,
                                 @RequestParam(required = false, name = "userStatus") Integer userStatus,
                                 @RequestParam(required = false, name = "realName") String realName,
                                 @RequestParam(name = "pageNum") Integer pageNum,
                                 @RequestParam(name = "pageSize") Integer pageSize);

    /**
     * 根据用户名获取管理员
     *
     * @param username 用户名
     * @return AdminVo
     */
    @GetMapping("/adminByUsername")
    R<AdminDTO> getAdminByUsername(@RequestParam(name = "username") String username);

    /**
     * 根据管理员id获取管理员
     *
     * @param adminId adminId
     * @return AdminVo
     */
    @GetMapping("/adminByAdminId")
    R<AdminDTO> getAdminByAdminId(@RequestParam(name = "adminId") Long adminId);

    /**
     * 根据手机号获取管理员
     *
     * @param mobile 手机号
     * @return AdminVo
     */
    @GetMapping("/adminByMobile")
    R<AdminDTO> getAdminByMobile(@RequestParam(name = "mobile") String mobile);

    /**
     * 获取管理员角色信息
     *
     * @param adminId adminId
     * @return List<AdminRoleDTO>
     */
    @GetMapping("getAdminRoles")
    R<List<AdminRoleDTO>> getAdminRoles(@RequestParam(name = "adminId") Long adminId);

    /**
     * 修改管理员角色信息
     *
     * @param param ModifyAdminParam
     * @return yes or no
     */
    @PutMapping("updateAdminRole")
    R<Boolean> updateAdminRole(@Valid @RequestBody ModifyAdminRoleParam param);

    /**
     * 获取管理员已有权限菜单
     *
     * @param adminId 管理员id
     * @return MenuTreeVo
     */
    @GetMapping("/admin/hasMenuTree")
    R<List<MenuTreeDTO>> getAdminHasMenu(@RequestParam(name = "adminId") Long adminId);
}
