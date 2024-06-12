package com.summary.biz.authority.provider;

import com.summary.biz.authority.service.MenuService;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import com.summary.client.remote.MenuRemoteService;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 菜单相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@DubboService
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
    public Long addMenu(@Valid AddMenuParam param) {
        return menuService.addMenuData(param);
    }

    /**
     * 修改菜单
     *
     * @param param ModifyMenuParam
     * @return yes or no
     */
    @Override
    public Boolean modifyMenu(@Valid ModifyMenuParam param) {
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
    public Boolean deleteMenuData(@Valid DeleteMenuParam param) {
        menuService.deleteMenuData(param);
        return true;
    }

    /**
     * 获取菜单树
     *
     * @return List<MenuTreeVo>
     */
    @Override
    public List<MenuTreeDTO> getMenuTree() {
        return menuService.getMenuTree();
    }

}
