package com.summary.biz.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role")
public class RoleDO extends BaseDO<RoleDO> {

    private static final long serialVersionUID = 1L;
    /*** 角色id */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Long roleId;
    /*** 角色名称 */
    private String roleName;
    /*** 角色别名 */
    private String roleAlias;
    /*** 备注 */
    private String remark;

    @Builder
    public RoleDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long roleId,
            String roleName,
            String roleAlias,
            String remark
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleAlias = roleAlias;
        this.remark = remark;
    }


    @Override
    public Serializable pkVal() {
        return this.roleId;
    }

}
