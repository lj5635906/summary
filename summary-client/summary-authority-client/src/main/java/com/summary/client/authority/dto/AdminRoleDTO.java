package com.summary.client.authority.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 管理员角色信息
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminRoleDTO implements Serializable {
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 当前管理员是否有该角色
     */
    private Boolean checked;
}