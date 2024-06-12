package com.summary.client.seckill.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 秒杀订单
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Data
public class SeckillOrderDTO implements Serializable {
    /**
     * 秒杀订单id
     */
    private Long orderId;
    /*** 秒杀id */
    private Long seckillId;
    /*** 商品id */
    private Long goodsId;
    /*** 商品skuId */
    private Long skuId;
    /*** 商品sku单价(单位/分) */
    private Long skuPrice;
    /*** 秒杀价格(单位/分) */
    private Long seckillPrice;
    /*** 秒杀订单支付金额 */
    private Long money;
    /*** 秒杀数量 */
    private Integer num;
    /*** 订单状态(-3-主动取消订单,-2-超时未支付,-1-已关闭,0-待付款,1-待发货,2-待收货,3-已完成,4-已评价,5-退款中,6-已退款) */
    private Integer orderState;
}
