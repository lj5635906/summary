package com.summary.client.goods.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品sku-简化版
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsSkuSimpleDTO {
    /*** 商品skuId */
    private Long skuId;
    /*** SKU名称 */
    private String skuName;
    /*** 价格(单位/分) */
    private Long price;
    /*** 库存数量 */
    private Long stockNum;
    /*** 销售状态: 0-销售中,-1-已售罄 */
    private Integer saleState;
}
