package com.summary.component.third.pay.constants;

/**
 * 支付宝 相关常量
 *
 * @author jie.luo
 * @since 2024/7/20
 */
public interface AliPayConstants {

    String PROTOCOL = "https";
    String GATEWAY_HOST = "openapi.alipay.com";
    String SIGN_TYPE = "RSA2";

    /**
     * 交易类型.
     */
    class TradeType {
        /**
         * App支付.
         */
        public static final String APP = "APP";
        /**
         * 手机网站支付.
         */
        public static final String WAP = "wap";
        /**
         * 电脑网站支付.
         */
        public static final String PAGE = "page";
    }

    /**
     * 业务结果代码.
     */
    class ResultCode {
        /**
         * 成功.
         */
        public static final String SUCCESS = "10000";
    }
}
