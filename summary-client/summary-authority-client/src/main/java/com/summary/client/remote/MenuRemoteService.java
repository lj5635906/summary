package com.summary.client.remote;

import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 菜单相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public interface MenuRemoteService {


    /**
     * 添加菜单
     *
     * @param param AddMenuParam
     * @return yes or no
     */
     Long addMenu(@Valid AddMenuParam param);

    /**
     * 修改菜单
     *
     * @param param ModifyMenuParam
     * @return yes or no
     */
     Boolean modifyMenu(@Valid ModifyMenuParam param);

    /**
     * 删除菜单
     *
     * @param param DeleteMenuParam
     * @return yes or no
     */
     Boolean deleteMenuData(@Valid DeleteMenuParam param);

    /**
     * 获取菜单树
     *
     * @return List<MenuTreeVo>
     */
     List<MenuTreeDTO> getMenuTree();

}
