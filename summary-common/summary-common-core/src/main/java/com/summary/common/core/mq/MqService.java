package com.summary.common.core.mq;

/**
 * MQ 发送消息相关接口
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface MqService {

    /**
     * 发送消息-异步
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     */
    void asyncSend(String destination, BaseMqMessage data);

    /**
     * 发送消息-同步
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     */
    void syncSend(String destination, BaseMqMessage data);

    /**
     * 发送延迟消息-异步
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     * @param delayLevel  消息延迟级别 {@link com.summary.common.core.mq.rocket.MessageDelayLevel}
     */
    void asyncSendDelayed(String destination, BaseMqMessage data, int delayLevel);

    /**
     * 发送延迟消息-同步
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     * @param delayLevel  消息延迟级别 {@link com.summary.common.core.mq.rocket.MessageDelayLevel}
     */
    void syncSendDelayed(String destination, BaseMqMessage data, int delayLevel);

    /**
     * 发送 oneway 消息
     * 式只发送请求
     * 不等待应答
     *
     * @param destination 消息发送目标
     * @param data        消息数据
     */
    void sendOneWay(String destination, BaseMqMessage data);
}
