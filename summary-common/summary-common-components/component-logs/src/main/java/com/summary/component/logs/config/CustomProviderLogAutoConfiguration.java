package com.summary.component.logs.config;

import com.summary.component.logs.ProviderLogAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rest 接口日志 自动配置
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Configuration
@ConditionalOnClass(org.apache.dubbo.config.annotation.DubboService.class)
@EnableConfigurationProperties(LogProperties.class)
public class CustomProviderLogAutoConfiguration {

    @Bean
    public ProviderLogAspect providerLogAspect() {
        return new ProviderLogAspect();
    }

}
