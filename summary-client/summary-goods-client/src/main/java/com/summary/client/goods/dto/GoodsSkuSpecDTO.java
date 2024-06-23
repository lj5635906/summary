package com.summary.client.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品sku规格
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
