package com.summary.biz.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 角色菜单关联表
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role_menu")
public class RoleMenuDO extends BaseDO<RoleMenuDO> {

    private static final long serialVersionUID = 1L;
    /*** 角色菜单关联id */
    @TableId(value = "role_menu_id", type = IdType.AUTO)
    private Long roleMenuId;
    /*** 角色id */
    private Long roleId;
    /*** 菜单表id */
    private Long menuId;
    /*** 类型:0-全选、1-办选 */
    private Integer type;

    @Builder
    public RoleMenuDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long roleMenuId,
            Long roleId,
            Long menuId,
            Integer type
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.roleMenuId = roleMenuId;
        this.roleId = roleId;
        this.menuId = menuId;
        this.type = type;
    }


    @Override
    public Serializable pkVal() {
        return this.roleMenuId;
    }

}
