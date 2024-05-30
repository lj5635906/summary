package com.summary.biz.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 管理员与角色关联表
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("auth_admin_role")
public class AdminRoleDO extends BaseDO<AdminRoleDO> {

    private static final long serialVersionUID = 1L;
    /*** 管理员与角色关联id */
    @TableId(value = "admin_role_id", type = IdType.AUTO)
    private Long adminRoleId;
    /*** 角色id */
    private Long roleId;
    /*** 管理员id */
    private Long adminId;

    @Builder
    public AdminRoleDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long adminRoleId,
            Long roleId,
            Long adminId
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.adminRoleId = adminRoleId;
        this.roleId = roleId;
        this.adminId = adminId;
    }


    @Override
    public Serializable pkVal() {
        return this.adminRoleId;
    }

}
