package com.summary.biz.seckill.listener;

import com.summary.biz.seckill.service.SeckillOrderService;
import com.summary.client.seckill.msg.SeckillOrderTimeoutCancelMsg;
import com.summary.common.core.constant.topic.OrderTopic;
import com.summary.common.core.constant.topic.SeckillOrderTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 订单创建后的延迟消息，检查订单是否需要取消
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = SeckillOrderTopic.TOPIC,
        selectorExpression = SeckillOrderTopic.SeckillOrderTimeoutCancel.SELECTOR_EXPRESSION,
        consumerGroup = SeckillOrderTopic.TOPIC + SeckillOrderTopic.SeckillOrderTimeoutCancel.SELECTOR_EXPRESSION
)
public class SeckillOrderTimeoutCancelListener implements RocketMQListener<SeckillOrderTimeoutCancelMsg> {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Override
    public void onMessage(SeckillOrderTimeoutCancelMsg msg) {
        seckillOrderService.seckillOrderTimeoutCancel(msg.getCustomerId(), msg.getSeckillId());
    }

}
