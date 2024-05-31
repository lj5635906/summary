package com.summary.client.goods.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 下单check商品参数
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateOrderCheckParam {
    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long goodsId;
    /**
     * 商品skuId
     */
    @NotNull(message = "商品skuId不能为空")
    private Long skuId;
}
