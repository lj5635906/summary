package com.summary.component.mq;

import com.summary.common.core.mq.MqService;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * rocket-mq 自动配置
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Configuration
// 导入配置类，在SpringBoot3及其后续版本中，不会默认导入RocketMQ的配置类，通过在启动类使用@Import导入或创建其配置类。
@Import(RocketMQAutoConfiguration.class)
@ConditionalOnClass
public class CustomRocketAutoConfiguration {

    @Bean
    public MqService mqService(RocketMQTemplate rocketMQTemplate) {
        return new RocketMqServiceImpl(rocketMQTemplate);
    }

}
