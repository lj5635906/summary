package com.summary.component.logs;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * @author jie.luo
 * @since 2024/6/13
 */
@Slf4j
@Aspect
@Order(10)
public class ProviderLogAspect {

    public ProviderLogAspect() {
        log.info("*************************** 加载日志启动 provider *******************************");
    }

    /**
     * 切入点
     */
    @Pointcut("execution(* com.summary..provider..*.*(..))")
    public void restPointcut() {
    }

    /**
     * 环绕事件
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 请求返回值
     * @throws Throwable t
     */
    @Around(value = "restPointcut()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        // 拦截方法的参数
        Object[] args = joinPoint.getArgs();

        // 拦截方法的前缀
        StringBuilder prefix = getLogPrefix(joinPoint);

        if (log.isDebugEnabled()) {
            String requestParam = getRequestParam(joinPoint);
            if (StrUtil.isNotBlank(requestParam)) {
                log.debug("{}，{}", prefix.toString(), requestParam);
            } else {
                log.debug("{}，无请求参数", prefix.toString());
            }
        }

        Object result = joinPoint.proceed(args);
        if (result != null) {
            if (log.isDebugEnabled()) {
                log.debug("{}，响应出参：{}", prefix.toString(), JSONUtil.toJsonStr(result));
            }
        } else {
            if (log.isDebugEnabled()) {
                log.debug("{}，响应出参：返回为 void", prefix.toString());
            }
        }

        return result;
    }

    /**
     * 获取请求参数信息
     *
     * @param joinPoint ProceedingJoinPoint
     * @return data
     */
    public String getRequestParam(ProceedingJoinPoint joinPoint) {

        // 获取所有参数的值
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();

        StringBuilder builder = new StringBuilder();
        if (args != null && args.length > 0) {
            int len = args.length;
            builder.append("请求入参有【");
            builder.append(len).append("】个参数，分别有：[ ");
            for (int i = 0; i < len; i++) {
                builder.append(parameterNames[i]);
                builder.append(" : ");
                builder.append(args[i]);
                if (i != (len - 1)) {
                    builder.append(" ] , [ ");
                } else {
                    builder.append(" ]");
                }
            }
        }

        return builder.toString();
    }

    /**
     * 获取日志前缀
     *
     * @param joinPoint ProceedingJoinPoint
     * @return URL : 请求地址：【/demo-context/demo/demo-get】，请求方法：【GET】
     * METHOD : 请求方法：【com.ajzx.business.demo.rest.DemoRest.demoGet()】
     */
    public StringBuilder getLogPrefix(ProceedingJoinPoint joinPoint) {

        StringBuilder builder = new StringBuilder();
        builder.append("请求ID：【");
        builder.append(UUID.randomUUID().toString().replace("-", ""));
        builder.append("】 ");

        Signature signature = joinPoint.getSignature();
        // 获取全限定类名
        String canonicalName = signature.getDeclaringType().getCanonicalName();
        // 方法名
        String methodName = signature.getName();
        builder.append("请求方法：【");
        builder.append(canonicalName);
        builder.append(".");
        builder.append(methodName);
        builder.append("()】");

        return builder;
    }

    @Data
    private class AspectObject {
        private RequestMethod method;
        private String url;
    }

}
