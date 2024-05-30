package com.summary.biz.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 菜单表
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("auth_menu")
public class MenuDO extends BaseDO<MenuDO> {

    private static final long serialVersionUID = 1L;
    /*** 菜单表id */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;
    /*** 所属资源id(父节点id)、根节点为0 */
    private Long parentId;
    /*** 父节点菜单名称,根节点：根节点 */
    private String parentMenuName;
    /*** 菜单名称 */
    private String menuName;
    /*** 菜单别名 */
    private String menuAlias;
    /*** 菜单图标url */
    private String menuIcon;
    /*** 菜单类别: 1-菜单、2-按钮、3-链接 */
    private Integer menuType;
    /*** 菜单类别描述 */
    private String menuTypeDesc;
    /*** 序号 */
    private Integer sort;
    /*** 资源描述 */
    private String description;
    /*** 菜单状态：0/false-废除、1/true-启用 */
    private Boolean menuStatus;
    /*** 路由地址 */
    private String menuRouterPath;
    /*** 按钮代码 */
    private String buttonCode;

    @Builder
    public MenuDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long menuId,
            Long parentId,
            String parentMenuName,
            String menuName,
            String menuAlias,
            String menuIcon,
            Integer menuType,
            String menuTypeDesc,
            Integer sort,
            String description,
            Boolean menuStatus,
            String menuRouterPath,
            String buttonCode
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.menuId = menuId;
        this.parentId = parentId;
        this.parentMenuName = parentMenuName;
        this.menuName = menuName;
        this.menuAlias = menuAlias;
        this.menuIcon = menuIcon;
        this.menuType = menuType;
        this.menuTypeDesc = menuTypeDesc;
        this.sort = sort;
        this.description = description;
        this.menuStatus = menuStatus;
        this.menuRouterPath = menuRouterPath;
        this.buttonCode = buttonCode;
    }


    @Override
    public Serializable pkVal() {
        return this.menuId;
    }

}
