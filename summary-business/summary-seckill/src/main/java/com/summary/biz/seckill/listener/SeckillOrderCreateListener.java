package com.summary.biz.seckill.listener;

import com.summary.biz.seckill.service.SeckillOrderService;
import com.summary.client.seckill.msg.CreateSeckillOrderMsg;
import com.summary.common.core.constant.topic.SeckillOrderTopic;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 创建秒杀订单
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = SeckillOrderTopic.TOPIC,
        selectorExpression = SeckillOrderTopic.SeckillOrderCreate.SELECTOR_EXPRESSION,
        consumerGroup = SeckillOrderTopic.TOPIC + SeckillOrderTopic.SeckillOrderCreate.SELECTOR_EXPRESSION
)
public class SeckillOrderCreateListener implements RocketMQListener<CreateSeckillOrderMsg> {

    @Autowired
    private SeckillOrderService seckillOrderService;

    @Override
    public void onMessage(CreateSeckillOrderMsg msg) {
        seckillOrderService.createSeckillOrder(msg);
    }
}
