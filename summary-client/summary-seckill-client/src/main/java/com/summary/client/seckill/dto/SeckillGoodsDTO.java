package com.summary.client.seckill.dto;

import lombok.Data;

/**
 * 描述商品详情
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Data
public class SeckillGoodsDTO {

    /*** 秒杀id */
    private Long seckillId;
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品skuId */
    private Long skuId;
    /*** SKU名称 */
    private String skuName;
    /*** 商品sku单价(单位/分) */
    private Long skuPrice;
    /*** 库存数量 */
    private Integer stockNum;
    /*** 标题 */
    private String title;
    /*** 秒杀价格(单位/分) */
    private Long seckillPrice;
}
