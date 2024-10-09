package com.summary.client.order.code;

import com.summary.common.core.exception.code.CustomCodeService;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单相关异常码
 * 40000-49999
 *
 * @author jie.luo
 * @since 2024/5/31
 */
public enum OrderExceptionCode implements CustomCodeService {
    /**
     * 业务模块-权限服务-异常码
     */
    by_goods_check_error(40000, "购买商品验证出现异常"),
    order_no_exist(40001, "订单不存在"),
    order_payed(40002, "订单已支付"),


    UNKNOWN_EXCEPTION(-1, "未知错误");

    private static final Map<Integer, OrderExceptionCode> EXCEPTION_CODE_MAP = new HashMap<>();

    static {
        for (OrderExceptionCode errorCode : OrderExceptionCode.values()) {
            EXCEPTION_CODE_MAP.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 根据响应码获取 BusinessCode
     *
     * @param code 响应码
     * @return BusinessCode
     */
    public static OrderExceptionCode getByCode(Integer code) {
        OrderExceptionCode result = EXCEPTION_CODE_MAP.get(code);
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

    OrderExceptionCode(Integer code, String message) {
        this(code, message, null);
    }

    OrderExceptionCode(Integer code, String message, String[] args) {
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

