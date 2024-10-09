package com.summary.biz.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单-支付子表
 *
 * @author myabtis-plus
 * @since 2024-07-22
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("order_payment_item")
public class OrderPaymentItemDO extends BaseDO<OrderPaymentItemDO> {

    private static final long serialVersionUID = 1L;
    /*** 订单-支付子表id */
    @TableId(value = "payment_item_id", type = IdType.AUTO)
    private Long paymentItemId;
    /*** 订单id */
    private Long orderId;
    /*** 订单-支付id */
    private Long paymentId;
    /*** 应用appId */
    private String appId;
    /*** 商户号id */
    private String mchId;
    /*** 支付方式 */
    private String payWay;
    /*** 支付信息 */
    private String payInfo;

    @Builder
    public OrderPaymentItemDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long paymentItemId,
            Long orderId,
            Long paymentId,
            String appId,
            String mchId,
            String payWay,
            String payInfo
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.paymentItemId = paymentItemId;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.appId = appId;
        this.mchId = mchId;
        this.payWay = payWay;
        this.payInfo = payInfo;
    }


    @Override
    public Serializable pkVal() {
        return this.paymentItemId;
    }

}
