package com.summary.biz.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 订单表
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("o_order")
public class OrderDO extends BaseDO<OrderDO> {

    private static final long serialVersionUID = 1L;
    /*** 订单id */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Long orderId;
    /*** 客户id */
    private Long customerId;
    /*** 姓名 */
    private String customerName;
    /*** 电话号码 */
    private String customerMobile;
    /*** 订单类型(1-普通订单、2-积分订单、3-秒杀订单、4-赠品) */
    private Integer orderType;
    /*** 订单类型描述 */
    private String orderTypeDesc;
    /*** 订单状态(-3-主动取消订单,-2-超时未支付,-1-已关闭,0-待付款,1-待发货,2-待收货,3-已完成,4-已评价,5-退款中,6-已退款) */
    private Integer orderState;
    /*** 订单状态描述 */
    private String orderStateDesc;
    /*** 订单总金额(分) */
    private Long money;
    /*** 订单优惠总金额(分) */
    private Long discountMoney;
    /*** 订单实际支付金额(分) */
    private Long payMoney;
    /*** 付款时间 */
    private LocalDateTime payTime;
    /*** 订单备注 */
    private String customerMessage;

    @Builder
    public OrderDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long orderId,
            Long customerId,
            String customerName,
            String customerMobile,
            Integer orderType,
            String orderTypeDesc,
            Integer orderState,
            String orderStateDesc,
            Long money,
            Long discountMoney,
            Long payMoney,
            LocalDateTime payTime,
            String customerMessage
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerMobile = customerMobile;
        this.orderType = orderType;
        this.orderTypeDesc = orderTypeDesc;
        this.orderState = orderState;
        this.orderStateDesc = orderStateDesc;
        this.money = money;
        this.discountMoney = discountMoney;
        this.payMoney = payMoney;
        this.payTime = payTime;
        this.customerMessage = customerMessage;
    }


    @Override
    public Serializable pkVal() {
        return this.orderId;
    }

}
