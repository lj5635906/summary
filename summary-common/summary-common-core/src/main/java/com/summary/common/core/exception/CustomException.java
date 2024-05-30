package com.summary.common.core.exception;

import com.summary.common.core.exception.code.CustomCodeService;

import java.text.MessageFormat;

/**
 * 自定义异常类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class CustomException extends RuntimeException {
    /**
     * 错误代码
     */
    private final int code;
    /**
     * 错误信息中的补位用的对应的值
     */
    private final Object[] args;
    /**
     * 错误信息
     */
    private final String message;


    public CustomException(CustomCodeService customCodeService) {
        super(getSuperMessage(customCodeService));
        this.code = customCodeService.getCode();
        this.message = customCodeService.getMessage();
        this.args = customCodeService.getArgs();
    }

    public CustomException(CustomCodeService customCodeService, String... args) {
        super(getSuperMessage(customCodeService));
        this.code = customCodeService.getCode();
        this.message = customCodeService.getMessage();
        this.args = args;
    }

    public CustomException(int code,String message, String... args) {
        this.code = code;
        this.message = message;
        this.args = args;
    }

    /**
     * 拼接异常信息
     *
     * @param customCodeService CustomCodeService
     * @return 异常信息
     */
    public static String getSuperMessage(CustomCodeService customCodeService) {

        if (null == customCodeService.getArgs()) {
            return customCodeService.getCode() + " : " + customCodeService.getMessage();
        }

        return customCodeService.getCode() + " : " + MessageFormat.format(customCodeService.getMessage(), customCodeService.getArgs());
    }

    public int getCode() {
        return code;
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
