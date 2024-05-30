package com.summary.client.authority.code;

import com.summary.common.core.exception.code.CustomCodeService;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务模块-权限服务-异常码
 * 40000-44999
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public enum AuthorityExceptionCode implements CustomCodeService {

    /**
     * i甲专线-业务模块-权限服务-异常码
     */
    ADMIN_NON_EXIT(40001, "管理员信息不存在"),
    MENU_NON_EXIT(40002, "菜单信息不存在"),
    ADMIN_PASSWORD_ERROR(40003, "密码错误"),
    ADMIN_MOBILE_EXISTS(40004, "电话号码已经存在"),
    ADMIN_ACCOUNT_EXISTS(40005, "账号已经存在"),
    ROLE_EXISTS(40006, "角色已经存在"),
    ROLE_NON_EXIT(40007, "角色信息不存在"),
    OLD_PASSWORD_ERROR(40008, "旧密码错误"),
    ADMIN_NON_EXIT_OR_PASSWORD_ERROR(40009, "管理员信息不存在或密码错误"),
    ADMIN_LOGIN_LOCKED(40010, "管理员账号已锁定{0}"),
    PASSWORD_RULE_ERROR(40011, "密码至少8个字符并至少包含一个大写字母、一个小写字母和一个数字"),

    UNKNOWN_EXCEPTION(-1, "未知错误");

    private static final Map<Integer, AuthorityExceptionCode> AUTHORITY_EXCEPTION_CODE_MAP = new HashMap<>();

    static {
        for (AuthorityExceptionCode errorCode : AuthorityExceptionCode.values()) {
            AUTHORITY_EXCEPTION_CODE_MAP.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 根据响应码获取 BusinessCode
     *
     * @param code 响应码
     * @return BusinessCode
     */
    public static AuthorityExceptionCode getByCode(Integer code) {
        AuthorityExceptionCode result = AUTHORITY_EXCEPTION_CODE_MAP.get(code);
        if (result == null) {
            return UNKNOWN_EXCEPTION;
        }
        return result;
    }

    /**
     * 错误代码
     */
    private final Integer code;
    /**
     * 错误信息中的补位用的对应的值
     */
    private final String[] args;
    /**
     * 错误信息
     */
    private final String message;

    AuthorityExceptionCode(Integer code, String message) {
        this(code, message, null);
    }

    AuthorityExceptionCode(Integer code, String message, String[] args) {
        this.code = code;
        this.args = args;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String[] getArgs() {
        return this.args;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
