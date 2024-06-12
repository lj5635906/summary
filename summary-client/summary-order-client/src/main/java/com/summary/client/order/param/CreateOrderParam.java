package com.summary.client.order.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;

/**
 * 下单参数
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Data
public class CreateOrderParam implements Serializable {
    /**
     * 客户id
     */
    @NotNull(message = "客户不能为空")
    private Long customerId;
    /**
     * 订单类型(1-普通订单、2-积分订单、3-秒杀订单、4-赠品订单)
     */
    @NotNull(message = "订单类型不能为空")
    @Range(message = "订单类型范围(1-4)", min = 1, max = 4)
    private Integer orderType;
    /**
     * 购买商品
     */
    @Valid
    private List<CreateOrderGoodsParam> goods;

    /*** 用户留言 */
    private String customerMessage;
}
