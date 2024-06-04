package com.summary.biz.order.listener;

import com.summary.biz.order.service.OrderService;
import com.summary.client.order.msg.OrderTimeoutCancelMsg;
import com.summary.common.core.constant.topic.OrderTopic;
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
        topic = OrderTopic.TOPIC,
        selectorExpression = OrderTopic.OrderTimeoutCancel.SELECTOR_EXPRESSION,
        consumerGroup = OrderTopic.TOPIC + OrderTopic.OrderTimeoutCancel.SELECTOR_EXPRESSION
)
public class OrderTimeoutCancelListener implements RocketMQListener<OrderTimeoutCancelMsg> {

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(OrderTimeoutCancelMsg msg) {
        orderService.orderTimeoutCancel(msg.getOrderId());
    }

}
