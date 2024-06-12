package com.summary.client.authority.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除菜单
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class DeleteMenuParam implements Serializable {

    /**
     * 菜单表id
     */
    @NotNull(message = "菜单id不合法")
    private Long menuId;

}
