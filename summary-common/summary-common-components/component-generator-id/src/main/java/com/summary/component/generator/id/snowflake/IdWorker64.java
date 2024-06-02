package com.summary.component.generator.id.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花算法 ID生成器
 * 64 位
 * 1bit     41bit      10bit         12bit
 * 0        时间撮      工作机器id      序列号
 *
 * @author jie.luo
 * @since 2024/06/03
 */
public class IdWorker64 {

    private static final Logger LOGGER = LoggerFactory.getLogger(IdWorker64.class);

    //下面两个每个5位，加起来就是10位的工作机器id
    /**
     * 工作id
     */
    private final long workerId;
    /**
     * 数据id
     */
    private final long dataCenterId;
    /**
     * 12位的序列号
     */
    private long sequence = 12L;

    public IdWorker64(long workerId, long dataCenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        LOGGER.info("worker starting. timestamp left shift {}, dataCenter id bits {}, worker id bits {}, sequence bits {}, workerId {}",
                timestampLeftShift, dataCenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    /**
     * 初始时间戳
     */
    private final long OFFSET = 1288834974657L;

    /**
     * 长度为5位
     */
    private final long workerIdBits = 5L;
    private final long dataCenterIdBits = 5L;
    /**
     * 最大值
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxDataCenterId = -1L ^ (-1L << dataCenterIdBits);
    /**
     * 序列号id长度
     */
    private final long sequenceBits = 12L;
    /**
     * 序列号最大值
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作id需要左移的位数，12位
     */
    private long workerIdShift = sequenceBits;
    /**
     * 数据id需要左移位数 12+5=17位
     */
    private long dataCenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间戳需要左移位数 12+5+5=22位
     */
    private long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;

    /**
     * 上次时间戳，初始值为负数
     */
    private long lastTimestamp = -1L;

    public long getWorkerId() {
        return workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 下一个ID生成算法
     *
     * @return 下一个ID
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //获取当前时间戳如果小于上次时间戳，则表示时间戳获取出现异常
        if (timestamp < lastTimestamp) {
            LOGGER.error("时钟回拨.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format("时钟回拨.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //获取当前时间戳如果等于上次时间戳（同一毫秒内），则在序列号加一；否则序列号赋值为0，从0开始。
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        //将上次时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        return ((timestamp - OFFSET) << timestampLeftShift) |
                (dataCenterId << dataCenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    /**
     * 获取时间戳，并与上次时间戳比较
     *
     * @param lastTimestamp 上一个时间戳
     * @return 时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取系统时间戳
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        IdWorker64 worker = new IdWorker64(0, 0);

        Long start = System.currentTimeMillis();
        for (int j = 0; j < 1000000; j++) {
            worker.nextId();
        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }
}
