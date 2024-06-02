package com.summary.component.generator.id.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static com.summary.component.generator.id.snowflake.GeneratorWorkData.DATA_ID_CACHE_KEY;

/**
 * dataId 续期线程
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public class DataIdRenewalThread implements Runnable {

    private final Long workId;
    private final Long dataId;
    private final RedisTemplate<String, String> redisTemplate;

    public DataIdRenewalThread(Long workId, Long dataId, RedisTemplate<String, String> redisTemplate) {
        this.workId = workId;
        this.dataId = dataId;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void run() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(String.format(DATA_ID_CACHE_KEY, workId) + dataId, String.valueOf(dataId), 60, TimeUnit.SECONDS);
    }
}
