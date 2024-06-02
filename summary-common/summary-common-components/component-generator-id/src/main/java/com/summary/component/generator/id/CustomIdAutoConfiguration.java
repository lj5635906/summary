package com.summary.component.generator.id;

import com.summary.component.generator.id.snowflake.DataIdRenewalThread;
import com.summary.component.generator.id.snowflake.IdWorker;
import com.summary.component.generator.id.snowflake.GeneratorWorkData;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ID 生成器 自动配置
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Slf4j
@Configuration
@ConditionalOnClass
public class CustomIdAutoConfiguration {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    public void generatorSnowFlake() throws Exception {

        GeneratorWorkData init = new GeneratorWorkData(redisTemplate);
        long workId = init.generatorWorkId();
        long dataId = init.generatorDataId(workId);

        log.info("ID 生成器 --- wordId: {} , dataCenterId: {} ", workId, dataId);
        IdWorker.setDataCenterId(dataId);
        IdWorker.setWorkerId(workId);

        // 每隔30秒检查 access_token 是否过期
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new DataIdRenewalThread(workId, dataId, redisTemplate), 1, 30, TimeUnit.SECONDS);
    }
}
