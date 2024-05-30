package com.summary.front.admin.rest.authority;

import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.RoleCheckMenuParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.client.remote.feign.RoleRemoteService;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@RestController
@RequestMapping("/admin/authority/role")
public class RoleController {

    @Autowired
    private RoleRemoteService roleRemoteService;

    /**
     * 添加角色
     *
     * @param param AddRoleParam
     * @return yes or no
     */
    @PostMapping("/addRole")
    public R<Long> addRole(@Valid @RequestBody AddRoleParam param) {
        return roleRemoteService.addRole(param);
    }

    /**
     * 修改角色
     *
     * @param param UpdateRoleParam
     * @return yes or no
     */
    @PutMapping("/updateRole")
    public R<Boolean> updateRole(@Valid @RequestBody UpdateRoleParam param) {
        return roleRemoteService.updateRole(param);
    }

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return PageResult<RoleVo>
     */
    @GetMapping("/page")
    public R<PageResult<RoleDTO>> page(@RequestParam(required = false, name = "roleName") String roleName,
                                       @RequestParam(name = "pageNum") Integer pageNum,
                                       @RequestParam(name = "pageSize") Integer pageSize) {
        return roleRemoteService.page(roleName, pageNum, pageSize);
    }

    /**
     * 获取菜单树并验证是否已经选中
     *
     * @param roleId 角色id
     * @return List<MenuTreeDTO>
     */
    @GetMapping("getRoleHasMenuTree")
    public R<List<MenuTreeDTO>> getRoleHasMenuTree(@RequestParam(name = "roleId") Long roleId) {
        return roleRemoteService.getRoleHasMenuTree(roleId);
    }

    /**
     * 获取角色已分配的菜单
     *
     * @param roleId 角色id
     * @return List<Long>
     */
    @GetMapping("getRoleHasMenus")
    public R<RoleCheckedMenuDTO> getRoleHasMenus(@RequestParam(name = "roleId") Long roleId) {
        return roleRemoteService.getRoleHasMenus(roleId);
    }

    /**
     * 角色分配菜单
     *
     * @param param RoleCheckMenuParam
     * @return yes or no
     */
    @PutMapping("roleSetMenu")
    public R<Boolean> roleSetMenu(@Valid @RequestBody RoleCheckMenuParam param) {
        return roleRemoteService.roleSetMenu(param);
    }
}
