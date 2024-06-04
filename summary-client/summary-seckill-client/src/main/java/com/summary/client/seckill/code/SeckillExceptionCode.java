package com.summary.client.seckill.code;

import com.summary.common.core.exception.code.CustomCodeService;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀相关异常码
 * 50000-59999
 *
 * @author jie.luo
 * @since 2024/5/31
 */
public enum SeckillExceptionCode implements CustomCodeService {
    /**
     * 业务模块-权限服务-异常码
     */
    seckill_customer_only(50000, "已经参与当前秒杀"),
    seckill_end(50000, "当前秒杀已经结束"),

    UNKNOWN_EXCEPTION(-1, "未知错误");

    private static final Map<Integer, SeckillExceptionCode> EXCEPTION_CODE_MAP = new HashMap<>();

    static {
        for (SeckillExceptionCode errorCode : SeckillExceptionCode.values()) {
            EXCEPTION_CODE_MAP.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 根据响应码获取 BusinessCode
     *
     * @param code 响应码
     * @return BusinessCode
     */
    public static SeckillExceptionCode getByCode(Integer code) {
        SeckillExceptionCode result = EXCEPTION_CODE_MAP.get(code);
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

    SeckillExceptionCode(Integer code, String message) {
        this(code, message, null);
    }

    SeckillExceptionCode(Integer code, String message, String[] args) {
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

