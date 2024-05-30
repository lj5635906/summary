package com.summary.common.core.utils;

import java.util.Map;

/**
 * 用户上下文
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public class UserContextHolder {

    private final ThreadLocal<Map<String, String>> threadLocal;

    private UserContextHolder() {
        this.threadLocal = new ThreadLocal<>();
    }

    /**
     * 创建实例
     *
     * @return UserContextHolder
     */
    public static UserContextHolder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 静态内部类单例模式
     * 单例初使化
     */
    private static class SingletonHolder {
        private static final UserContextHolder INSTANCE = new UserContextHolder();
    }

    /**
     * 用户上下文中放入信息
     *
     * @param map 上下文数据
     */
    public void setContext(Map<String, String> map) {
        threadLocal.set(map);
    }

    /**
     * 获取上下文中的信息
     *
     * @return 上下文数据
     */
    public Map<String, String> getContext() {
        return threadLocal.get();
    }

    /**
     * 清空上下文
     */
    public void clear() {
        threadLocal.remove();
    }
}
