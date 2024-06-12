package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品sku规格
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsSkuSpecDTO implements Serializable {
    /**
     * 商品sku规格名称
     */
    private String name;
    /**
     * 商品sku规格值
     */
    private String option;
}
