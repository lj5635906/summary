package com.summary.component.mq;

import com.alibaba.fastjson.JSON;
import com.summary.common.core.mq.BaseMqMessage;
import com.summary.common.core.mq.MqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * Rocket MQ 发送消息相关接口 实现
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Slf4j
public class RocketMqServiceImpl implements MqService {

    private final RocketMQTemplate rocketMqTemplate;

    public RocketMqServiceImpl(RocketMQTemplate rocketMqTemplate) {
        this.rocketMqTemplate = rocketMqTemplate;
    }

    @Override
    public void asyncSend(String destination, BaseMqMessage data) {
        rocketMqTemplate.asyncSend(
                destination,
                MessageBuilder
                        .withPayload(data)
                        .setHeader("KEYS", data.getMsgId())
                        .build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult res) {
                        log.debug("convertAndSend - onSuccess - destination：{} data:{} sendResult:{}", destination, JSON.toJSONString(data), JSON.toJSONString(res));
                    }

                    @Override
                    public void onException(Throwable t) {
                        log.error("convertAndSend - onException - destination：{} data:{} e:", destination, JSON.toJSONString(data), t);
                    }
                });
    }

    @Override
    public void syncSend(String destination, BaseMqMessage data) {
        rocketMqTemplate.syncSend(
                destination,
                MessageBuilder
                        .withPayload(data)
                        .setHeader("KEYS", data.getMsgId())
                        .build());
    }

    @Override
    public void asyncSendDelayed(String destination, BaseMqMessage data, int delayLevel) {
        rocketMqTemplate.asyncSend(
                destination,
                MessageBuilder
                        .withPayload(data)
                        .setHeader("KEYS", data.getMsgId())
                        .build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult res) {
                        log.debug("sendDelayed - onSuccess - topic：{} data:{} sendResult:{}", destination, JSON.toJSONString(data), JSON.toJSONString(res));
                    }

                    @Override
                    public void onException(Throwable e) {
                        log.error("sendDelayed - onException - topic：{} data:{} e:", destination, JSON.toJSONString(data), e);
                    }
                }, 5000, delayLevel);
    }

    @Override
    public void syncSendDelayed(String destination, BaseMqMessage data, int delayLevel) {
        rocketMqTemplate.syncSend(
                destination,
                MessageBuilder
                        .withPayload(data)
                        .setHeader("KEYS", data.getMsgId())
                        .build(),
                5000, delayLevel);
    }

    @Override
    public void sendOneWay(String destination, BaseMqMessage data) {
        rocketMqTemplate.sendOneWay(
                destination,
                MessageBuilder
                        .withPayload(data)
                        .setHeader("KEYS", data.getMsgId())
                        .build());
    }

}
