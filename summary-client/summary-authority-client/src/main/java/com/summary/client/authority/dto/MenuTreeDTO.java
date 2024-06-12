package com.summary.client.authority.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单树
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class MenuTreeDTO implements Serializable {

    /**
     * 菜单表id
     */
    private Long menuId;

    /**
     * 所属资源id(父节点id)、根节点为0
     */
    private Long parentId;

    /**
     * 父节点菜单名称,根节点：根节点
     */
    private String parentMenuName;

    /**
     * 菜单名称
     */
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
    private Integer menuType;

    /**
     * 菜单类型描述
     */
    private String menuTypeDesc;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 菜单状态：0-废除、1-启用
     */
    private Boolean menuStatus;

    /**
     * 路由地址
     */
    private String menuRouterPath;
    /**
     * 按钮代码
     */
    private String buttonCode;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 是否选中
     */
    private Boolean checked = false;
    /**
     * 选中类型:0-全选、1-半选
     */
    private Integer checkedType;
    /**
     * 子节点
     */
    private List<MenuTreeDTO> children = new ArrayList<>();

}
