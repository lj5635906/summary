package com.summary.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 应用分类枚举
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Getter
@AllArgsConstructor
public enum AppEnum {

    /**
     * 应用分类枚举
     */
    ADMIN(0, "后台管理"),

    WEB(1, "web端");

    /**
     * code
     */
    private final int code;
    /**
     * 描述
     */
    private final String message;

    private static final Map<Integer, AppEnum> VALUE_MAP = new HashMap<>();

    static {
        for (AppEnum enums : AppEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static AppEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
