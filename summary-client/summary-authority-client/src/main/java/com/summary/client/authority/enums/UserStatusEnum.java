package com.summary.client.authority.enums;

/**
 * 用户状态枚举
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public enum UserStatusEnum {
    /**
     * 用户状态: 1-锁定、2-正常 、3-注销
     */
    locking(1, "锁定"),
    normal(2, "正常"),
    logout(3, "注销");

    private final Integer code;

    private final String message;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
