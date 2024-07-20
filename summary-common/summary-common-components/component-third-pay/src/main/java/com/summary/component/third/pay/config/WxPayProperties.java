package com.summary.component.third.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信 商户号相关配置
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@ConfigurationProperties(prefix = "summary.pay.wx")
public class WxPayProperties {
    /**
     * 商户号: "190000****"
     */
    private String mchId;
    /**
     * 商户 API v3 密钥
     */
    private String mchSecret;
    /**
     * 商户证书序列号 : "5157F09EFDC096DE15EBE81A47057A72********"
     */
    private String mchSerialNo;
    /**
     * 商户API私钥路径: "/Users/yourname/your/path/apiclient_key.pem"
     */
    private String privateKeyPath;
    /**
     * 平台证书: "/Users/yourname/your/path/apiclient.c12"
     */
    private String mchCertPath;
    /**
     * 支付结果通知地址，通知url必须为直接可访问的url，不能携带参数.
     */
    private String notifyUrl;
}
