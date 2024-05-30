package com.summary.common.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Getter
public enum SexEnum {
    /**
     * 性别
     */
    UNKNOWN(-1, "未知"),

    WOMAN(0, "女性"),

    MAN(1, "男性");

    SexEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * code
     */
    private final int code;
    /**
     * 描述
     */
    private final String message;

    private static final Map<Integer, SexEnum> VALUE_MAP = new HashMap<>();

    static {
        for (SexEnum enums : SexEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static SexEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
