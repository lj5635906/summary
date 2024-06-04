package com.summary.common.core.mq;

/**
 * MQ 发送消息相关接口
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface MqService {

    /**
     * 发送消息
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     */
    void send(String destination, BaseMqMessage data);

    /**
     * 发送延迟消息
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     * @param delayLevel  消息延迟级别 {@link com.summary.common.core.mq.rocket.MessageDelayLevel}
     */
    void sendDelayed(String destination, BaseMqMessage data, int delayLevel);
}
