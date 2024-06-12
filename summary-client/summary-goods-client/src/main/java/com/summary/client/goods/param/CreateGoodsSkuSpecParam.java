package com.summary.client.goods.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品sku规格
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateGoodsSkuSpecParam implements Serializable {
    /**
     * 商品sku规格名称
     */
    private String name;
    /**
     * 商品sku规格值
     */
    private String option;
}
