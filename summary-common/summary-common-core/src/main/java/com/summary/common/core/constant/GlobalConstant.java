package com.summary.common.core.constant;

/**
 * 全局常量
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class GlobalConstant {

    /**
     * 初始化常量
     */
    public interface DefaultConstant {
        /**
         * 默认0
         */
        Integer ZERO = 0;
        /**
         * 默认0
         */
        Long ZERO_LONG = 0L;

    }

    /**
     * 菜单常量
     */
    public interface MenuConstant {
        /**
         * 全局跟节点id
         */
        Long ROOT_ID = 0L;
        /**
         * 全局跟节点名
         */
        String ROOT_NAME = "跟节点";
    }

    /**
     * 字符串相关常量
     */
    public interface StrConstant {
        /**
         * 默认字符集：UTF-8
         */
        String DEFAULT_CHARSET = "UTF-8";
        /**
         * 字符串 .
         */
        String STRING_POINT = ".";
        /**
         * 英文逗号
         */
        String STRING_COMMA = ",";
        /**
         * header 中 access_token 名
         */
        String HEADER_ACCESS_TOKEN_NAME = "Authorization";
    }

    /**
     * session中存储字段名
     */
    public interface SessionConstant {
        String X_ADMIN_ID = "x-admin-id";
        String X_REQUEST_IP = "x-request-ip";
    }

    /**
     * redis 缓存Key名称前缀
     */
    public interface RedisCacheConstant {
        /**
         * 分布式锁前缀
         */
        String LOCK_NAME = "global:lock:";
    }

}
