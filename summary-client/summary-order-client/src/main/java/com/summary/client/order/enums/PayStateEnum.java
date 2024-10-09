package com.summary.client.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付状态
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Getter
@AllArgsConstructor
public enum PayStateEnum {
    /**
     * 支付状态(0-支付中,1-已支付,2-退款中,3-已退款)
     */
    paying(0, "支付中"),
    payed(1, "已支付"),
    refunding(2, "退款中"),
    refunded(3, "已退款");

    private final Integer code;

    private final String message;

    private static final Map<Integer, PayStateEnum> VALUE_MAP = new HashMap<>();

    static {
        for (PayStateEnum enums : PayStateEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static PayStateEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
