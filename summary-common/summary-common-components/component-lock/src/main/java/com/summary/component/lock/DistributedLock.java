package com.summary.component.lock;

/**
 * 分布式锁顶层接口
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface DistributedLock {

    /**
     * 获取锁
     * @param lockName 锁名称
     * @return 是否获取到锁
     */
    boolean acquire(String lockName);

    /**
     * 获取锁
     *
     * @param lockName   锁名称
     * @param expireTime 锁过期时间:毫秒
     * @return 是否获取到锁
     */
    boolean acquire(String lockName, int expireTime);

    /**
     * 释放锁
     *
     * @param lockName 锁名称
     */
    void release(String lockName);
}
