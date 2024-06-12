package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建订单check商品 返回的数据
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateOrderCheckGoodsSkuDTO implements Serializable {
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品skuId */
    private Long skuId;
    /*** SKU名称 */
    private String skuName;
    /*** 价格(单位/分) */
    private Long price;
    /*** 库存数量 */
    private Long stockNum;
}
