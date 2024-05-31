package com.summary.client.goods.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片类型
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Getter
@AllArgsConstructor
public enum ImageTypeEnum {
    /**
     * 图片类型: 0-商品SPU、1-商品SKU
     */
    spu(0, "商品SPU"),
    sku(1, "商品SKU");

    private final Integer code;

    private final String message;

    private static final Map<Integer, ImageTypeEnum> VALUE_MAP = new HashMap<>();

    static {
        for (ImageTypeEnum enums : ImageTypeEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static ImageTypeEnum getByCode(Integer code) {
        return VALUE_MAP.get(code);
    }
}
