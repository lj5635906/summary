package com.summary.client.authority.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 管理员修改角色参数
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class ModifyAdminRoleParam implements Serializable {

    /**
     * 用户id
     */
    @NotNull(message = "adminId不能为空")
    private Long adminId;

    /**
     * 管理员已配置的角色集合
     */
    private List<Long> roleIds;
}
