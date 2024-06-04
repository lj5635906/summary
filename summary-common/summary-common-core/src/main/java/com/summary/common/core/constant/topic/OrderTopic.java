package com.summary.common.core.constant.topic;

/**
 * 消息队列 Topic
 * 订单相关
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface OrderTopic {
    /**
     * 订单 topic
     */
    String TOPIC = "OrderTopic";

    /**
     * 订单超时自动取消
     */
    interface OrderTimeoutCancel {
        /*** 过滤表达式 */
        String SELECTOR_EXPRESSION = "OrderTimeoutCancel";
        /*** 消息目的地 */
        String DESTINATION = TOPIC + ":" + SELECTOR_EXPRESSION;
    }
}
