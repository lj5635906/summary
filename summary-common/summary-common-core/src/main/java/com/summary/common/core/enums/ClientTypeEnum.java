package com.summary.common.core.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户端类型
 *
 * @author jie.luo
 * @since 2024/07/22
 */
@Getter
public enum ClientTypeEnum {
    /**
     * 公众号
     */
    MP(0, "MP","公众号"),
    /**
     * 小程序
     */
    APPLET(1,"APPLET", "小程序"),
    /**
     * APP
     */
    APP(2, "APP","APP");

    private final Integer code;

    private final String prefix;

    private final String message;

    ClientTypeEnum(Integer code, String prefix, String message) {
        this.code = code;
        this.prefix = prefix;
        this.message = message;
    }

    private static final Map<Integer, ClientTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (ClientTypeEnum enums : ClientTypeEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static ClientTypeEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
