package com.summary.component.generator.id.snowflake;

import com.summary.common.core.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;


/**
 * workid 初始化工作
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Slf4j
public class GeneratorWorkData {

    public static final String WORK_ID_CACHE_KEY = "snowflake:work:host:";
    public static final String HOST_WORK_LOCK_CACHE_KEY = "snowflake:work:lock";
    public static final String DATA_ID_CACHE_KEY = "snowflake:data:%s:";
    public static final String HOST_DATA_LOCK_CACHE_KEY = "snowflake:data:lock:";
    private static final long INIT_WORK_ID = 0;
    private static final long MAX_WORK_ID = 31;
    private static final long INIT_DATA_ID = 0;
    private static final long MAX_DATA_ID = 31;

    private final RedisTemplate<String, String> redisTemplate;

    public GeneratorWorkData(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 构建 work_id
     * workId 规则：通过启动服务IP地址来计算，
     *
     * @return work_id
     * @throws Exception .
     */
    public synchronized long generatorWorkId() throws Exception {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // 本机IP
        String hostIp = getHostIp();

        // 获取 work_id 操作锁
        Boolean lock = valueOperations.setIfAbsent(HOST_WORK_LOCK_CACHE_KEY, hostIp, 1000, TimeUnit.MILLISECONDS);

        while (!lock) {
            // 未获取到锁 等待1秒后继续获取
            Thread.sleep(1000);
            lock = valueOperations.setIfAbsent(HOST_WORK_LOCK_CACHE_KEY, hostIp, 1000, TimeUnit.MILLISECONDS);
        }

        Object workIdObj = redisTemplate.opsForHash().get(WORK_ID_CACHE_KEY, hostIp);
        if (null != workIdObj) {
            return Long.parseLong((String) workIdObj);
        }

        Long size = redisTemplate.opsForHash().size(WORK_ID_CACHE_KEY);
        if (size == INIT_WORK_ID) {
            // 当前IP中没有任何节点
            redisTemplate.opsForHash().put(WORK_ID_CACHE_KEY, hostIp, String.valueOf(INIT_WORK_ID));
            return INIT_WORK_ID;
        }

        if (size > MAX_DATA_ID) {
            // 当前机房已经启动超过32个服务器节点
            throw new CustomException(GREATER_THAN_MAX_WORK_ID);
        }

        // 当前IP中已经至少有一个节点
        // 因为work_id是从0开始，所以当前work_id可以直接使用节点数
        redisTemplate.opsForHash().put(WORK_ID_CACHE_KEY, hostIp, String.valueOf(size));
        return size;
    }

    /**
     * 构建 dataId
     *
     * @return dataId
     * @throws Exception .
     */
    public synchronized long generatorDataId(long workId) throws Exception {

        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        // 获取 work_id 操作锁
        Boolean lock = valueOperations.setIfAbsent(HOST_DATA_LOCK_CACHE_KEY + workId, String.valueOf(workId), 1000, TimeUnit.MILLISECONDS);

        while (!lock) {
            // 未获取到锁 等待1秒后继续获取
            Thread.sleep(1000);
            lock = valueOperations.setIfAbsent(HOST_DATA_LOCK_CACHE_KEY + workId, String.valueOf(workId), 1000, TimeUnit.MILLISECONDS);
        }

        Set<String> keys = redisTemplate.keys(String.format(DATA_ID_CACHE_KEY, workId) + "*");

        if (CollectionUtils.isEmpty(keys)) {
            log.debug("当前设备 {} 未启动服务， dataId : {}", workId, INIT_DATA_ID);
            valueOperations.set(String.format(DATA_ID_CACHE_KEY, workId) + INIT_DATA_ID, String.valueOf(INIT_DATA_ID), 60, TimeUnit.SECONDS);
            return INIT_DATA_ID;
        }

        Set<Long> dataSet = new HashSet<>();
        for (String key : keys) {
            String dataId = valueOperations.get(key);
            if (StringUtils.isNotBlank(dataId)) {
                dataSet.add(Long.valueOf(dataId));
            }
        }

        log.debug("当前设备 {} 启动服务数量 : {}", workId, dataSet.size());

        if (dataSet.size() > MAX_DATA_ID) {
            // 当前设备已经启动超过32个服务节点
            throw new CustomException(GREATER_THAN_MAX_DATA_ID);
        }

        Long[] dataArray = {0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L, 21L, 22L, 23L, 24L, 25L, 26L, 27L, 28L, 29L, 30L, 31L};
        Set<Long> defaultDataSet = new HashSet<>(Arrays.asList(dataArray));

        // 取出差集
        defaultDataSet.removeAll(dataSet);

        // 获取最小大data_id
        Long nextDataId = defaultDataSet.iterator().next();

        valueOperations.set(String.format(DATA_ID_CACHE_KEY, workId) + nextDataId, String.valueOf(nextDataId), 60, TimeUnit.SECONDS);
        return nextDataId;
    }

    /**
     * 获取本机IP
     *
     * @return 本机IP
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException unknownHostException) {
                log.error(e.getMessage(), e);
                throw new CustomException(GET_HOST_IP_ERROR);
            }
        }
    }
}
