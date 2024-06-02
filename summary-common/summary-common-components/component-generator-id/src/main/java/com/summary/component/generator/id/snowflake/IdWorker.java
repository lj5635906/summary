package com.summary.component.generator.id.snowflake;

/**
 * 生成雪花算法id
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public class IdWorker {

    /**
     * 工作id
     */
    private static long workerId;
    /**
     * 数据id
     */
    private static long dataCenterId;

    private static class SingletonInstance {
        private static final IdWorker64 WORKER = new IdWorker64(workerId, dataCenterId);
    }

    /**
     * 获取id
     *
     * @return id
     */
    public static Long nextId() {
        return SingletonInstance.WORKER.nextId();
    }

    public static void setWorkerId(long workerId) {
        IdWorker.workerId = workerId;
    }

    public static void setDataCenterId(long dataCenterId) {
        IdWorker.dataCenterId = dataCenterId;
    }

    public static long getWorkerId() {
        return workerId;
    }

    public static long getDataCenterId() {
        return dataCenterId;
    }

}
