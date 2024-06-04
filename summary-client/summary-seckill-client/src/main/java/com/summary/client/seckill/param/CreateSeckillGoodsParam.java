package com.summary.client.seckill.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建秒杀商品
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Data
public class CreateSeckillGoodsParam {

    /*** 秒杀id */
    private Long seckillId;
    /*** 商品id */
    @NotNull(message = "商品id 不合法")
    private Long goodsId;
    /*** 商品名称 */
    @NotBlank(message = "商品名称 不合法")
    private String goodsName;
    /*** 商品skuId */
    @NotNull(message = "商品skuId 不合法")
    private Long skuId;
    /*** 商品SKU名称 */
    @NotBlank(message = "商品SKU名称 不合法")
    private String skuName;
    /*** 商品sku单价(单位/分) */
    @NotNull(message = "商品sku单价 不合法")
    private Long skuPrice;
    /*** 库存数量 */
    @NotNull(message = "库存数量 不合法")
    private Integer stockNum;
    /*** 标题 */
    @NotBlank(message = "标题 不合法")
    private String title;
    /*** 秒杀价格(单位/分) */
    @NotNull(message = "秒杀价格 不合法")
    private Long seckillPrice;
}
