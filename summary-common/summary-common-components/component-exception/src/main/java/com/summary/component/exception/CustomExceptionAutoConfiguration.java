package com.summary.component.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 异常处理器自动配置类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Configuration
@ConditionalOnClass
@ComponentScan(basePackages = "com.summary.component.exception")
public class CustomExceptionAutoConfiguration {
}
