package com.summary.client.authority.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * 角色分配菜单
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class RoleCheckMenuParam {

    /**
     * 角色id
     */
    @NotNull(message = "角色id不合法")
    private Long roleId;
    /**
     * 菜单id集合(全选)
     */
    private Set<Long> checked = new HashSet<>();
    /**
     * 菜单id集合(半选)
     */
    private Set<Long> halfChecked = new HashSet<>();
}
