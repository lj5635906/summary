package com.summary.client.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Getter
@AllArgsConstructor
public enum OrderStateEnum {
    /**
     * 订单状态(-3-主动取消订单,-2-超时未支付,-1-已关闭,0-待付款,1-待发货,2-待收货,3-已完成,4-已评价,5-退款中,6-已退款)
     */
    CANCEL(-3, "主动取消订单"),
    TIME_OUT(-2, "超时未支付"),
    CLOSED(-1, "已关闭"),
    WAIT_PAY(0, "待付款"),
    PAY(1, "待发货"),
    WAIT_SHIP(2, "待收货"),
    FINISH(3, "已完成"),
    Evaluated(4, "已评价"),
    REFUNDING(5, "退款中"),
    REFUND(6, "已退款");

    private final Integer code;

    private final String message;

    private static final Map<Integer, OrderStateEnum> VALUE_MAP = new HashMap<>();

    static {
        for (OrderStateEnum enums : OrderStateEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static OrderStateEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
