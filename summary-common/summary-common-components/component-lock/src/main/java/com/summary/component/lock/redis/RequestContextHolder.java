package com.summary.component.lock.redis;

/**
 * 请求用户上下文
 *
 * @author jie.luo
 * @since 2024/6/3
 */
public class RequestContextHolder {

    private final ThreadLocal<String> threadLocal;

    private RequestContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     *
     * @return RequestContextHolder
     */
    public static RequestContextHolder getInstance() {
        return RequestContextHolder.SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final RequestContextHolder INSTANCE = new RequestContextHolder();
    }

    /**
     * 请求上下文中放入信息
     *
     * @param str 上下文数据
     */
    public void setContext(String str) {
        threadLocal.set(str);
    }

    /**
     * 获取上下文中的信息
     *
     * @return 上下文数据
     */
    public String getContext() {
        return threadLocal.get();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
