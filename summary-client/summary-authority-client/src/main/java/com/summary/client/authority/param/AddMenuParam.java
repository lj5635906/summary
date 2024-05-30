package com.summary.client.authority.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 添加菜单
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class AddMenuParam {

    /**
     * 所属资源id(父节点id)、根节点为0
     */
    @NotNull(message = "父节点不合法")
    @Range(message = "父节点不合法",min = 0)
    private Long parentId;
    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;

    /**
     * 菜单别名
     */
    private String menuAlias;

    /**
     * 菜单图标url
     */
    private String menuIcon;

    /**
     * 菜单类别: 1-菜单、2-按钮、3-链接
     */
    @NotNull(message = "菜单类别不合法")
    @Range(message = "菜单类别不合法",min = 1,max = 3)
    private Integer menuType;

    /**
     * 序号
     */
    @Range(message = "序号不合法",min = 0,max = 9999)
    private Integer sort;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 菜单状态：0-废除、1-启用
     */
    private Boolean menuStatus = false;

    /**
     * 路由地址
     */
    private String menuRouterPath;

    /**
     *  按钮代码
     */
    private String buttonCode;

}
