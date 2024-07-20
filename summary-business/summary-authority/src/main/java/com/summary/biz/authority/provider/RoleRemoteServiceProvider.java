package com.summary.biz.authority.provider;

import com.summary.biz.authority.service.MenuService;
import com.summary.biz.authority.service.RoleMenuService;
import com.summary.biz.authority.service.RoleService;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.RoleCheckMenuParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.client.remote.RoleRemoteService;
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
@RequestMapping("/authority/role")
public class RoleRemoteServiceProvider implements RoleRemoteService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;

    /**
     * 添加角色
     *
     * @param param AddRoleParam
     * @return yes or no
     */
    @Override
    @PostMapping("/addRole")
    public Long addRole(@Valid @RequestBody AddRoleParam param) {
        return roleService.addRole(param);
    }

    /**
     * 修改角色
     *
     * @param param UpdateRoleParam
     * @return yes or no
     */
    @Override
    @PutMapping("/updateRole")
    public Boolean updateRole(@Valid @RequestBody UpdateRoleParam param) {
        roleService.updateRole(param);
        return true;
    }

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return PageResult<RoleVo>
     */
    @Override
    @GetMapping("/page")
    public PageResult<RoleDTO> page(@RequestParam(required = false, name = "roleName") String roleName,
                                    @RequestParam(name = "pageNum") Integer pageNum,
                                    @RequestParam(name = "pageSize") Integer pageSize) {
        return roleService.getRoles(roleName, pageNum, pageSize);
    }

    /**
     * 获取菜单树并验证是否已经选中
     *
     * @param roleId 角色id
     * @return List<MenuTreeDTO>
     */
    @Override
    @GetMapping("/getRoleHasMenuTree")
    public List<MenuTreeDTO> getRoleHasMenuTree(@RequestParam(name = "roleId") Long roleId) {
        return menuService.roleHasMenuTree(roleId);
    }

    /**
     * 获取角色已分配的菜单
     *
     * @param roleId 角色id
     * @return List<Long>
     */
    @Override
    @GetMapping("getRoleHasMenus")
    public RoleCheckedMenuDTO getRoleHasMenus(@RequestParam(name = "roleId") Long roleId) {
        return roleMenuService.getRoleHasMenus(roleId);
    }

    /**
     * 角色分配菜单
     *
     * @param param RoleCheckMenuParam
     * @return yes or no
     */
    @Override
    @PutMapping("roleSetMenu")
    public Boolean roleSetMenu(@RequestBody RoleCheckMenuParam param) {
        roleMenuService.roleSetMenu(param);
        return true;
    }
}
