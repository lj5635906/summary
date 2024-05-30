package com.summary.client.authority.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 菜单类型
 *
 * @author jie.wang
 * @since 2020/11/11
 */
public enum MenuTypeEnum {
    /**
     * 菜单类型
     */
    MENU(1, "菜单"),
    BUTTON(2, "按钮"),
    LINK(3, "链接");

    private final Integer code;

    private final String message;

    MenuTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private static final Map<Integer, MenuTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (MenuTypeEnum enums : MenuTypeEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static MenuTypeEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
