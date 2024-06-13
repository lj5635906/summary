package com.summary.component.logs.config;

import com.summary.component.logs.RestControllerLogAspect;
import org.springframework.beans.factory.annotation.Autowired;
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
@ConditionalOnClass(org.springframework.web.bind.annotation.RestController.class)
@EnableConfigurationProperties(LogProperties.class)
public class CustomWebLogAutoConfiguration {

    @Autowired
    private LogProperties logProperties;

    @Bean
    public RestControllerLogAspect restControllerLogAspect() {
        return new RestControllerLogAspect(logProperties.getType());
    }

}
