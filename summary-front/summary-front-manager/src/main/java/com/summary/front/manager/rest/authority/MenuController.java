package com.summary.front.manager.rest.authority;


import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import com.summary.client.remote.MenuRemoteService;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 菜单相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@RestController
@RequestMapping("/admin/authority/menu")
public class MenuController {

    @Autowired
    private MenuRemoteService menuRemoteService;

    /**
     * 添加菜单
     *
     * @param param AddMenuParam
     * @return menuId
     */
    @PostMapping("/addMenu")
    public R<Long> addMenu(@Valid @RequestBody AddMenuParam param) {
        return menuRemoteService.addMenu(param);
    }

    /**
     * 修改菜单
     *
     * @param param ModifyMenuParam
     * @return yes or no
     */
    @PutMapping("/modifyMenu")
    public R<Boolean> modifyMenu(@Valid @RequestBody ModifyMenuParam param) {
        return menuRemoteService.modifyMenu(param);
    }

    /**
     * 删除菜单
     *
     * @param param DeleteMenuParam
     * @return yes or no
     */
    @DeleteMapping("/deleteMenuData")
    public R<Boolean> deleteMenuData(@Valid @RequestBody DeleteMenuParam param) {
        return menuRemoteService.deleteMenuData(param);
    }

    /**
     * 获取菜单树
     *
     * @return List<MenuTreeVo>
     */
    @GetMapping("getMenuTree")
    public R<List<MenuTreeDTO>> getMenuTree() {
        return menuRemoteService.getMenuTree();
    }

}
