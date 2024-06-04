package com.summary.client.goods.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 商品下单扣库存与增加销量
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Data
public class ChangeStockAndSaleParam {

    /**
     * 购买商品id
     */
    @NotNull(message = "商品id不能为空")
    private Long goodsId;
    /**
     * 购买商品skuId
     */
    @NotNull(message = "商品skuId不能为空")
    private Long skuId;
    /**
     * 扣库存与增加销量
     */
    @NotNull(message = "扣库存与增加销量不能为空")
    @Range(message = "扣库存与增加销量范围(1-n)", min = 1)
    private Integer num;
}
