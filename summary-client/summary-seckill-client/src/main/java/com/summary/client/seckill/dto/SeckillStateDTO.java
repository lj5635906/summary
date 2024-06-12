package com.summary.client.seckill.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 秒杀状态信息
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Data
@NoArgsConstructor
public class SeckillStateDTO implements Serializable {
    /**
     * 秒杀状态 : -3-支付超时，-2-秒杀已结束，-1-秒杀失败，0-正在排队，1-等待支付，2-支付完成
     */
    private Integer state;
    /**
     * 用户id
     */
    private Long customerId;
    /**
     * 秒杀id
     */
    private Long seckillId;
    /**
     * 创建时间=参与秒杀时间
     */
    private LocalDateTime createTime;
    /**
     * 秒杀应付金额
     */
    private Long money;
    /**
     * 秒杀订单号
     */
    private Long orderId;

    @Builder

    public SeckillStateDTO(Integer state, Long customerId, Long seckillId, LocalDateTime createTime, Long money, Long orderId) {
        this.state = state;
        this.customerId = customerId;
        this.seckillId = seckillId;
        this.createTime = createTime;
        this.money = money;
        this.orderId = orderId;
    }
}
