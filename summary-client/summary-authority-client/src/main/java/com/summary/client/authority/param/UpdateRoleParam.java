package com.summary.client.authority.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改角色参数
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class UpdateRoleParam implements Serializable {
    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    /**
     * 角色名称
     */
    @NotNull(message = "角色名称不能为空")
    private String roleName;
    /**
     * 角色别名
     */
    @NotNull(message = "角色别名不能为空")
    private String roleAlias;

    /**
     * 备注
     */
    private String remark;
}
