package com.summary.biz.authority.provider;

import com.summary.biz.authority.service.MenuService;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import com.summary.client.remote.MenuRemoteService;
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
@RequestMapping("/authority/menu")
public class MenuRemoteServiceProvider implements MenuRemoteService {

    @Autowired
    private MenuService menuService;

    /**
     * 添加菜单
     *
     * @param param AddMenuParam
     * @return yes or no
     */
    @Override
    @PostMapping("/addMenu")
    public Long addMenu(@Valid @RequestBody AddMenuParam param) {
        return menuService.addMenuData(param);
    }

    /**
     * 修改菜单
     *
     * @param param ModifyMenuParam
     * @return yes or no
     */
    @Override
    @PutMapping("/modifyMenu")
    public Boolean modifyMenu(@Valid @RequestBody ModifyMenuParam param) {
        menuService.modifyMenuData(param);
        return true;
    }

    /**
     * 删除菜单
     *
     * @param param DeleteMenuParam
     * @return yes or no
     */
    @Override
    @DeleteMapping("/deleteMenuData")
    public Boolean deleteMenuData(@Valid @RequestBody DeleteMenuParam param) {
        menuService.deleteMenuData(param);
        return true;
    }

    /**
     * 获取菜单树
     *
     * @return List<MenuTreeVo>
     */
    @Override
    @GetMapping("getMenuTree")
    public List<MenuTreeDTO> getMenuTree() {
        return menuService.getMenuTree();
    }

}
