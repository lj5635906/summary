package com.summary.biz.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 订单流水
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("order_water")
public class OrderWaterDO extends BaseDO<OrderWaterDO> {

    private static final long serialVersionUID = 1L;
    /*** 订单流水id */
    @TableId(value = "water_id", type = IdType.AUTO)
    private Long waterId;
    /*** 订单id */
    private Long orderId;
    /*** 操作前订单状态 */
    private Integer fromOrderState;
    /*** 操作前订单状态描述 */
    private String fromOrderStateDesc;
    /*** 操作后订单状态 */
    private Integer toOrderState;
    /*** 操作后订单状态描述 */
    private String toOrderStateDesc;
    /*** 备注 */
    private String remark;

    @Builder
    public OrderWaterDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long waterId,
            Long orderId,
            Integer fromOrderState,
            String fromOrderStateDesc,
            Integer toOrderState,
            String toOrderStateDesc,
            String remark
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.waterId = waterId;
        this.orderId = orderId;
        this.fromOrderState = fromOrderState;
        this.fromOrderStateDesc = fromOrderStateDesc;
        this.toOrderState = toOrderState;
        this.toOrderStateDesc = toOrderStateDesc;
        this.remark = remark;
    }


    @Override
    public Serializable pkVal() {
        return this.waterId;
    }

}
