package com.summary.client.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 是否上架
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Getter
@AllArgsConstructor
public enum MarketableEnum {
    /**
     * 是否上架: 0-已上架,1-已下架
     */
    up(0, "已上架"),
    down(1, "已下架");

    private final Integer code;

    private final String message;

    private static final Map<Integer, MarketableEnum> VALUE_MAP = new HashMap<>();

    static {
        for (MarketableEnum enums : MarketableEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static MarketableEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
