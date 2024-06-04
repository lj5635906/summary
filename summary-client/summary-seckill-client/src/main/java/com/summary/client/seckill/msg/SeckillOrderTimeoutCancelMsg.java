package com.summary.client.seckill.msg;

import com.summary.common.core.mq.BaseMqMessage;
import lombok.*;

/**
 * 秒杀订单创建后的延迟消息，检查订单是否需要取消
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SeckillOrderTimeoutCancelMsg extends BaseMqMessage {

    private Long seckillId;

    private Long customerId;

    @Builder
    public SeckillOrderTimeoutCancelMsg(String messageId, Long seckillId, Long customerId) {
        super(messageId);
        this.seckillId = seckillId;
        this.customerId = customerId;
    }
}
