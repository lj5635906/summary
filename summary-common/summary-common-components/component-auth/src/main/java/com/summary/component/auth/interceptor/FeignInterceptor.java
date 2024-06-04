package com.summary.component.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * feign 远程调用拦截器
 * 调用前将token写入请求header中
 *
 * @author jie.luo
 * @since 2024/6/5
 */
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 将用户请求对象中所有的请求头放入RequestTemplate的请求头中
        // 获取到用户请求中所有的请求头
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        Enumeration<String> headerNames = requestAttributes.getRequest().getHeaderNames();
        // 放入
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = requestAttributes.getRequest().getHeader(key);
            requestTemplate.header(key, value);
        }
    }
}
