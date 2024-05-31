package com.summary.client.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 销售状态
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Getter
@AllArgsConstructor
public enum SaleStateEnum {
    /**
     * 销售状态: 0-销售中,-1-已售罄
     */
    sell_out(-1, "已售罄"),
    sale(1, "销售中");

    private final Integer code;

    private final String message;

    private static final Map<Integer, SaleStateEnum> VALUE_MAP = new HashMap<>();

    static {
        for (SaleStateEnum enums : SaleStateEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static SaleStateEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
