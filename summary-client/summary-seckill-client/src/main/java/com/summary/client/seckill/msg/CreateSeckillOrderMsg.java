package com.summary.client.seckill.msg;

import com.summary.common.core.mq.BaseMqMessage;
import lombok.*;

/**
 * 创建秒杀订单
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreateSeckillOrderMsg extends BaseMqMessage {

    /**
     * 用户id
     */
    private Long customerId;
    /**
     * 秒杀id
     */
    private Long seckillId;
    /**
     * 秒杀数量
     */
    private Integer num;

    @Builder
    public CreateSeckillOrderMsg(String messageId, Long customerId, Long seckillId, Integer num) {
        super(messageId);
        this.customerId = customerId;
        this.seckillId = seckillId;
        this.num = num;
    }
}
