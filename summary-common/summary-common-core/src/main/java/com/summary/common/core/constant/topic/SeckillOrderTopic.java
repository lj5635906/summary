package com.summary.common.core.constant.topic;

/**
 * 消息队列 Topic
 * 秒杀订单相关
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface SeckillOrderTopic {
    /**
     * 订单 topic
     */
    String TOPIC = "SeckillOrderTopic";

    /**
     * 秒杀订单超时自动取消
     */
    interface SeckillOrderCreate {
        /*** 过滤表达式 */
        String SELECTOR_EXPRESSION = "SeckillOrderCreate";
        /*** 消息目的地 */
        String DESTINATION = TOPIC + ":" + SELECTOR_EXPRESSION;
    }

    /**
     * 秒杀订单超时自动取消
     */
    interface SeckillOrderTimeoutCancel {
        /*** 过滤表达式 */
        String SELECTOR_EXPRESSION = "SeckillOrderTimeoutCancel";
        /*** 消息目的地 */
        String DESTINATION = TOPIC + ":" + SELECTOR_EXPRESSION;
    }
}
