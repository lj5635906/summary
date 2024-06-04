package com.summary.client.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀状态
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Getter
@AllArgsConstructor
public enum SeckillStateEnums {
    /**
     * 秒杀状态 : -3-支付超时，-2-秒杀已结束，-1-秒杀失败，0-正在排队，1-等待支付，2-支付完成
     */
    pay_timeout(-3, "支付超时"),
    end(-2, "秒杀已结束"),
    fail(-1, "秒杀失败"),
    queuing(0, "正在排队"),
    wait_pay(1, "等待支付"),
    FINISH(2, "支付完成");


    private final Integer code;

    private final String message;

    private static final Map<Integer, SeckillStateEnums> VALUE_MAP = new HashMap<>();

    static {
        for (SeckillStateEnums enums : SeckillStateEnums.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static SeckillStateEnums getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
