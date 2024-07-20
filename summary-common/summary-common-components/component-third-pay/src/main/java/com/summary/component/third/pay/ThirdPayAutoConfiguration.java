package com.summary.component.third.pay;

import com.summary.component.third.pay.config.AliPayProperties;
import com.summary.component.third.pay.config.WxPayProperties;
import com.summary.component.third.pay.service.ThirdPayService;
import com.summary.component.third.pay.service.ThirdPayServiceImpl;
import com.summary.component.third.pay.service.ali.AliPayService;
import com.summary.component.third.pay.service.wx.WxPayService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 三方支付
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties({AliPayProperties.class, WxPayProperties.class})
public class ThirdPayAutoConfiguration {

    @Bean
    public ThirdPayService thirdPayService(AliPayProperties aliPayProperties, WxPayProperties wxPayProperties) {

        // 加载支付宝支付配置信息
        AliPayService.loadAliPayOptions(aliPayProperties);

        return new ThirdPayServiceImpl();
    }

    @Bean
    public WxPayService wxPayService(WxPayProperties wxPayProperties) {

        Config config = new RSAAutoCertificateConfig.Builder()
                .merchantId(wxPayProperties.getMchId())
                .privateKeyFromPath(wxPayProperties.getPrivateKeyPath())
                .merchantSerialNumber(wxPayProperties.getMchSerialNo())
                .apiV3Key(wxPayProperties.getMchSecret())
                .build();

        return new WxPayService(config, wxPayProperties);
    }


}
