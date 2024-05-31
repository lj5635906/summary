package com.summary.client.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单类型(1-普通订单、2-积分订单、3-秒杀订单、4-赠品)
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
    /**
     * 订单类型
     */
    general(1, "普通订单"),
    integral(2, "积分订单"),
    seckill(3, "秒杀订单"),
    gift(4, "赠品订单"),;


    private final Integer code;

    private final String message;

    private static final Map<Integer, OrderTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (OrderTypeEnum enums : OrderTypeEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static OrderTypeEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
