package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品简化版
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsSimpleDTO implements Serializable {
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品SKU */
    private List<GoodsSkuSimpleDTO> skus;
}
