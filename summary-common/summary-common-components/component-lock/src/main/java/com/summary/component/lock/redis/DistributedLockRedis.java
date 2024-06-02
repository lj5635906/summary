package com.summary.component.lock.redis;

import com.summary.common.core.utils.UUIDUtils;
import com.summary.component.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.summary.common.core.constant.GlobalConstant.RedisCacheConstant.LOCK_NAME;

/**
 * 基于 redis 的分布式锁
 *
 * @author jie.luo
 * @since 2024/6/3
 */
@Slf4j
@Component
public class DistributedLockRedis implements DistributedLock {

    private RedisTemplate redisTemplate;

    public DistributedLockRedis(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 锁默认过期时间：1秒
     */
    private static final Integer DEFAULT_LOCK_EXPIRE = 1000;
    /**
     * 重新尝试获取锁的时间
     */
    private final long timeSleep = 100;

    /**
     * 获取锁
     * 若未获取到锁
     * 则每隔100毫秒再次重新获取锁，最多尝试3次
     * 则最长继续等待300毫秒
     *
     * @param lockName 锁名称
     * @return 是否获取到锁
     */
    @Override
    public boolean acquire(String lockName) {
        return this.acquire(lockName, DEFAULT_LOCK_EXPIRE);
    }

    /**
     * 获取锁
     * 若未获取到锁
     * 则每隔100毫秒再次重新获取锁，最多尝试3次
     * 则最长继续等待300毫秒
     *
     * @param lockName   锁名称
     * @param expireTime 锁过期时间:毫秒
     * @return 是否获取到锁
     */
    @Override
    public boolean acquire(String lockName, int expireTime) {
        String requestId = UUIDUtils.generateUuid() + ":" + Thread.currentThread().getId();
        RequestContextHolder.getInstance().setContext(requestId);
        boolean lock;
        int sleepCount = 0;
        while (true) {
            Boolean absent = redisTemplate.opsForValue().setIfAbsent(buildKey(lockName), requestId, expireTime, TimeUnit.MILLISECONDS);
            lock = null != absent && absent;
            if (lock || sleepCount >= 3) {
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(timeSleep);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            sleepCount++;
        }

        if (!lock) {
            // 未获取到锁则清空上下文
            RequestContextHolder.getInstance().clear();
        }

        return lock;
    }

    @Override
    public void release(String lockName) {
        String requestId = RequestContextHolder.getInstance().getContext();
        String key = buildKey(lockName);
        if (requestId.equals(redisTemplate.opsForValue().get(key))) {
            redisTemplate.delete(key);
        }
    }

    private String buildKey(String lockName) {
        return LOCK_NAME + lockName;
    }
}
