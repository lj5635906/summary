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
        /**
         * 验证码前缀
         */
        String VALIDATE_CODE_PREFIX = "validate_code:";
    }

    /**
     * session中存储字段名
     */
    public interface TokenConstant {
        String TOKEN_TYPE = "x-token-type";
        String X_USER_ID = "x-user-id";
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
        /**
         * 秒杀商品
         * hash
         * key=seckill:goods
         * map=<seckillId,goods>
         */
        String SECKILL_GOODS = "seckill:goods";
        /**
         * 秒杀商品库存列表
         * list
         * key=seckill:goods:stock:seckillId
         * value=商品的库存列表，该商品的每个库存存储一个元素
         */
        String SECKILL_GOODS_STOCK = "seckill:goods:stock:";
        /**
         * 秒杀用户标识
         * hash
         * key=seckill:user::seckillId
         * map=<userid,参与秒杀次数>
         */
        String SECKILL_USER = "seckill:user:";
        /**
         * 秒杀状态
         * hash
         * key=seckill:state::seckillId
         * map=<userid,秒杀状态信息>
         */
        String SECKILL_STATE = "seckill:state:";
        /**
         * 秒杀订单
         * hash
         * key=seckill:order::seckillId
         * map=<userid,秒杀订单信息>
         */
        String SECKILL_ORDER = "seckill:order:";
    }

}
