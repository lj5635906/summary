package com.summary.client.order.msg;

import com.summary.common.core.mq.BaseMqMessage;
import lombok.*;

/**
 * 订单创建后的延迟消息，检查订单是否需要取消
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderTimeoutCancelMsg extends BaseMqMessage {

    private Long orderId;

    @Builder
    public OrderTimeoutCancelMsg(String messageId, Long orderId) {
        super(messageId);
        this.orderId = orderId;
    }
}
