package com.summary.common.core.mq.rocket;

/**
 * rocket mq 延迟队列 延迟等级
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public interface MessageDelayLevel {
    /**
     * 1s
     */
    int LEVEL_1 = 1;
    /**
     * 5s
     */
    int LEVEL_2 = 2;
    /**
     * 10s
     */
    int LEVEL_3 = 3;
    /**
     * 30s
     */
    int LEVEL_4 = 4;
    /**
     * 1m
     */
    int LEVEL_5 = 5;
    /**
     * 2m
     */
    int LEVEL_6 = 6;
    /**
     * 3m
     */
    int LEVEL_7 = 7;
    /**
     * 4m
     */
    int LEVEL_8 = 8;
    /**
     * 5m
     */
    int LEVEL_9 = 9;
    /**
     * 6m
     */
    int LEVEL_10 = 10;
    /**
     * 7m
     */
    int LEVEL_11 = 11;
    /**
     * 8m
     */
    int LEVEL_12 = 12;
    /**
     * 9m
     */
    int LEVEL_13 = 13;
    /**
     * 10m
     */
    int LEVEL_14 = 14;
    /**
     * 20m
     */
    int LEVEL_15 = 15;
    /**
     * 30m
     */
    int LEVEL_16 = 16;
    /**
     * 1h
     */
    int LEVEL_17 = 17;
    /**
     * 2h
     */
    int LEVEL_18 = 18;

}
