package com.summary.client.goods.code;

import com.summary.common.core.exception.code.CustomCodeService;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品相关异常码
 * 30000-39999
 *
 * @author jie.luo
 * @since 2024/5/31
 */
public enum GoodsExceptionCode implements CustomCodeService {
    /**
     * 业务模块-权限服务-异常码
     */
    goods_non_existent(30000, "商品信息为空"),
    goods_stock_lack(30001, "商品库存不足"),

    UNKNOWN_EXCEPTION(-1, "未知错误");

    private static final Map<Integer, GoodsExceptionCode> EXCEPTION_CODE_MAP = new HashMap<>();

    static {
        for (GoodsExceptionCode errorCode : GoodsExceptionCode.values()) {
            EXCEPTION_CODE_MAP.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 根据响应码获取 BusinessCode
     *
     * @param code 响应码
     * @return BusinessCode
     */
    public static GoodsExceptionCode getByCode(Integer code) {
        GoodsExceptionCode result = EXCEPTION_CODE_MAP.get(code);
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

    GoodsExceptionCode(Integer code, String message) {
        this(code, message, null);
    }

    GoodsExceptionCode(Integer code, String message, String[] args) {
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

