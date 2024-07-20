package com.summary.component.third.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 支付宝 商户号相关配置
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@ConfigurationProperties(prefix = "summary.pay.ali")
public class AliPayProperties {
    /**
     * appId
     */
    private String appId;
    /**
     * 商户私钥
     */
    private String privateKey;
    /**
     * 支付宝公钥
     */
    private String aliPayPublicKey;
    /**
     * 回调地址
     */
    private String notifyUrl;
}
