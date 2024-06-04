package com.summary.component.auth;

import com.summary.component.auth.config.AuthenticationAnonProperties;
import com.summary.component.auth.filters.AccessTokenCheckFilter;
import com.summary.component.auth.interceptor.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 认证自动配置
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Configuration
@ConditionalOnClass
@EnableConfigurationProperties(AuthenticationAnonProperties.class)
public class SummaryAuthAutoConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignInterceptor();
    }

    @Bean
    public FilterRegistrationBean<AccessTokenCheckFilter> buildAccessTokenCheckFilter(AuthenticationAnonProperties properties) {
        FilterRegistrationBean<AccessTokenCheckFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AccessTokenCheckFilter(properties));
        filterRegistrationBean.setOrder(10);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
