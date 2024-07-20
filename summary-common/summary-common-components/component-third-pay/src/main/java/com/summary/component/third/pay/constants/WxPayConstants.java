package com.summary.component.third.pay.constants;


/**
 * 微信 相关常量
 *
 * @author jie.luo
 * @since 2024/7/20
 */
public interface WxPayConstants {

    /**
     * 微信支付接口请求地址域名部分
     */
    String DEFAULT_PAY_BASE_URL = "https://api.mch.weixin.qq.com";

    /**
     * 交易类型.
     */
    class TradeType {
        /**
         * 原生扫码支付.
         */
        public static final String NATIVE = "NATIVE";
        /**
         * App支付.
         */
        public static final String APP = "APP";
        /**
         * 公众号支付/小程序支付.
         */
        public static final String JSAPI = "JSAPI";
        /**
         * H5支付.
         */
        public static final String H5 = "H5";
    }

    /**
     * 业务结果代码.
     */
    class ResultCode {
        /**
         * 成功.
         */
        public static final String SUCCESS = "SUCCESS";

        /**
         * 失败.
         */
        public static final String FAIL = "FAIL";
    }
}
