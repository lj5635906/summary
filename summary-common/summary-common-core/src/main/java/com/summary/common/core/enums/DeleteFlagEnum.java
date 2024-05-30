package com.summary.common.core.enums;

import lombok.Getter;

/**
 * 删除标志枚举
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Getter
public enum DeleteFlagEnum {
    /**
     * 已删除
     */
    TRUE(true, "已删除"),
    /**
     * 未删除
     */
    FALSE(false, "未删除");

    private final Boolean code;

    private final String message;

    DeleteFlagEnum(Boolean code, String message) {
        this.code = code;
        this.message = message;
    }

}
