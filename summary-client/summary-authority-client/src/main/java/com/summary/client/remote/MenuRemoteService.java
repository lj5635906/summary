package com.summary.client.remote;

import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.authority.param.AddMenuParam;
import com.summary.client.authority.param.DeleteMenuParam;
import com.summary.client.authority.param.ModifyMenuParam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单相关接口
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@FeignClient(name = "summary-authority", path = "/authority/menu")
public interface MenuRemoteService {

    /**
     * 添加菜单
     *
     * @param param AddMenuParam
     * @return yes or no
     */
    @PostMapping("/addMenu")
    Long addMenu(@Valid @RequestBody AddMenuParam param);

    /**
     * 修改菜单
     *
     * @param param ModifyMenuParam
     * @return yes or no
     */
    @PutMapping("/modifyMenu")
    Boolean modifyMenu(@Valid @RequestBody ModifyMenuParam param);

    /**
     * 删除菜单
     *
     * @param param DeleteMenuParam
     * @return yes or no
     */
    @DeleteMapping("/deleteMenuData")
    Boolean deleteMenuData(@Valid @RequestBody DeleteMenuParam param);

    /**
     * 获取菜单树
     *
     * @return List<MenuTreeVo>
     */
    @GetMapping("getMenuTree")
    List<MenuTreeDTO> getMenuTree();

}
