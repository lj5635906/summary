package com.summary.biz.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单-支付信息
 *
 * @author myabtis-plus
 * @since 2024-07-22
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("order_payment")
public class OrderPaymentDO extends BaseDO<OrderPaymentDO> {

    private static final long serialVersionUID = 1L;
    /*** 订单-支付id */
    @TableId(value = "payment_id", type = IdType.AUTO)
    private Long paymentId;
    /*** 订单id */
    private Long orderId;
    /*** 支付客户端类型 */
    private Integer payClientType;
    /*** 支付平台 */
    private String payPlatform;
    /*** 支付平台描述 */
    private String payPlatformDesc;
    /*** 支付方式 */
    private String payWay;
    /*** 支付状态:0:支付中、1:已支付、2:退款中、3:已退款 */
    private Integer payState;
    /*** 发起支付时间 */
    private LocalDateTime startPayTime;
    /*** 支付时间 */
    private LocalDateTime payTime;
    /*** 发起退款时间 */
    private LocalDateTime startRefundTime;
    /*** 退款时间 */
    private LocalDateTime refundTime;
    /*** 订单-支付子表id */
    private Long paymentItemId;

    @Builder
    public OrderPaymentDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long paymentId,
            Long orderId,
            Integer payClientType,
            String payPlatform,
            String payWay,
            Integer payState,
            LocalDateTime startPayTime,
            LocalDateTime payTime,
            LocalDateTime startRefundTime,
            LocalDateTime refundTime,
            Long paymentItemId
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.payClientType = payClientType;
        this.payPlatform = payPlatform;
        this.payWay = payWay;
        this.payState = payState;
        this.startPayTime = startPayTime;
        this.payTime = payTime;
        this.startRefundTime = startRefundTime;
        this.refundTime = refundTime;
        this.paymentItemId = paymentItemId;
    }


    @Override
    public Serializable pkVal() {
        return this.paymentId;
    }

}
