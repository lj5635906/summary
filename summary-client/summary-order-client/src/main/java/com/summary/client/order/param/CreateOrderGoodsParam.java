package com.summary.client.order.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 创建订单商品参数
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Data
public class CreateOrderGoodsParam {

    /**
     * 购买商品id
     */
    @NotNull(message = "购买商品id不能为空")
    private Long goodsId;
    /**
     * 购买商品skuId
     */
    @NotNull(message = "购买商品skuId不能为空")
    private Long skuId;
    /**
     * 购买商品数量
     */
    @NotNull(message = "购买商品数量不能为空")
    @Range(message = "购买商品数量范围(1-n)", min = 1)
    private Integer buyNumber;

}
