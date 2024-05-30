package com.summary.client.remote.feign;

import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.dto.RoleCheckedMenuDTO;
import com.summary.client.authority.dto.RoleDTO;
import com.summary.client.authority.param.AddRoleParam;
import com.summary.client.authority.param.RoleCheckMenuParam;
import com.summary.client.authority.param.UpdateRoleParam;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 角色相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@FeignClient(name = "summary-authority", path = "/authority/role")
public interface RoleRemoteService {

    /**
     * 添加角色
     *
     * @param param AddRoleParam
     * @return yes or no
     */
    @PostMapping("/addRole")
    R<Long> addRole(@RequestBody AddRoleParam param);

    /**
     * 修改角色
     *
     * @param param UpdateRoleParam
     * @return yes or no
     */
    @PutMapping("/updateRole")
    R<Boolean> updateRole(@RequestBody UpdateRoleParam param);

    /**
     * 获取角色列表
     *
     * @param roleName 角色名
     * @param pageNum  页码
     * @param pageSize 页条数
     * @return PageInfo<RoleVo>
     */
    @GetMapping("/page")
    R<PageResult<RoleDTO>> page(@RequestParam(required = false, name = "roleName") String roleName, @RequestParam(name = "pageNum") Integer pageNum, @RequestParam(name = "pageSize") Integer pageSize);

    /**
     * 获取菜单树并验证是否已经选中
     *
     * @param roleId 角色id
     * @return List<MenuTreeDTO>
     */
    @GetMapping("/getRoleHasMenuTree")
    R<List<MenuTreeDTO>> getRoleHasMenuTree(@RequestParam(name = "roleId") Long roleId);

    /**
     * 获取角色已分配的菜单
     *
     * @param roleId 角色id
     * @return RoleCheckedMenuDTO
     */
    @GetMapping("getRoleHasMenus")
    R<RoleCheckedMenuDTO> getRoleHasMenus(@RequestParam(name = "roleId") Long roleId);

    /**
     * 角色分配菜单
     *
     * @param param RoleCheckMenuParam
     * @return yes or no
     */
    @PutMapping("roleSetMenu")
    R<Boolean> roleSetMenu(@RequestBody RoleCheckMenuParam param);

}
