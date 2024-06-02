package com.summary.component.lock;

import com.summary.component.lock.redis.DistributedLockRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 锁相关配置
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Configuration
@ConditionalOnClass
public class DistributedLockAutoConfiguration {

    @Autowired
    private RedisTemplate redisTemplate;

    @Bean
    public DistributedLock distributedLock() {
        return new DistributedLockRedis(redisTemplate);
    }
}
