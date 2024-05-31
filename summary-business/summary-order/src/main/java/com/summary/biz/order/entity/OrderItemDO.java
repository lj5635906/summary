package com.summary.biz.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 订单-类目表
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("order_item")
public class OrderItemDO extends BaseDO<OrderItemDO> {

    private static final long serialVersionUID = 1L;
    /*** 主键id */
    @TableId(value = "item_id", type = IdType.AUTO)
    private Long itemId;
    /*** 订单id */
    private Long orderId;
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
    /*** 该商品总金额(分) */
    private Long money;
    /*** 该商品优惠总金额(分) */
    private Long discountMoney;
    /*** 该商品实际支付金额(分) */
    private Long payMoney;
    /*** 商品的数量/购买数量 */
    private Integer buyNumber;

    @Builder
    public OrderItemDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long itemId,
            Long orderId,
            Long goodsId,
            String goodsName,
            Long skuId,
            String skuName,
            Long skuPrice,
            Long money,
            Long discountMoney,
            Integer buyNumber
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.itemId = itemId;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuPrice = skuPrice;
        this.money = money;
        this.discountMoney = discountMoney;
        this.buyNumber = buyNumber;
    }


    @Override
    public Serializable pkVal() {
        return this.itemId;
    }

}
