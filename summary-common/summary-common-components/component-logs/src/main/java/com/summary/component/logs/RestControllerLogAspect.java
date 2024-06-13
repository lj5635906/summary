package com.summary.component.logs;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * rest 接口打印参数以及响应信息
 * 打印rest接口参数与返回值日志信息
 * 如需打印得配置该包日志级别为DEBUG
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Slf4j
@Aspect
@Order(10)
public class RestControllerLogAspect {

    private final LogType logType;

    @Value("${server.servlet.context-path:#{null}}")
    public String contextPath;

    public RestControllerLogAspect(LogType logType) {
        this.logType = logType;
        log.info("*************************** 加载日志启动 web LogType:{} *******************************", this.logType);
    }

    /**
     * 切入点
     */
    @Pointcut("execution(* com.summary..rest..*.*(..))")
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
        StringBuilder prefix = getLogPrefix(joinPoint, logType);

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
     * @param logType   LogType
     * @return URL : 请求地址：【/demo-context/demo/demo-get】，请求方法：【GET】
     * METHOD : 请求方法：【com.ajzx.business.demo.rest.DemoRest.demoGet()】
     */
    public StringBuilder getLogPrefix(ProceedingJoinPoint joinPoint, LogType logType) {

        StringBuilder builder = new StringBuilder();
        builder.append("请求ID：【");
        builder.append(UUID.randomUUID().toString().replace("-", ""));
        builder.append("】 ");

        if (LogType.URL.equals(logType)) {
            // 获取 AOP 拦截方法的 URL，针对 @RestController
            AspectObject aspectObject = getAspectMethodUrl(joinPoint);

            if (ObjectUtils.allNotNull(aspectObject)) {

                builder.append("请求地址：【");
                builder.append(aspectObject.getUrl());
                builder.append("】，");
                builder.append("请求方法：【");
                builder.append(aspectObject.getMethod());
                builder.append("】");
            }

        } else {
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
        }

        return builder;
    }

    @Data
    private class AspectObject {
        private RequestMethod method;
        private String url;
    }

    /**
     * 获取 AOP 拦截方法的 URL，针对 @RestController
     *
     * @param joinPoint ProceedingJoinPoint
     * @return AspectObject
     * url : server.servlet.context-path + 类上 RequestMapping + 方法上 RequestMapping
     * method : 请求方法
     */
    public AspectObject getAspectMethodUrl(ProceedingJoinPoint joinPoint) {
        try {
            // 获取拦截目标类
            Class<?> targetClass = joinPoint.getTarget().getClass();

            // 验证目标类上是否有 @RestController 注解
            if (targetClass.isAnnotationPresent(RestController.class)) {
                // 请求URL
                String requestUrl = "";
                if (targetClass.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = targetClass.getDeclaredAnnotation(RequestMapping.class);
                    String[] value = requestMapping.value();
                    requestUrl = StringUtils.join(value);
                }

                // 获取拦截目标的方法
                Signature signature = joinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature) signature;
                Method currentMethod = targetClass.getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

                // 请求方法
                RequestMethod requestMethods = null;
                // 获取方法上设置的URL
                if (currentMethod.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping requestMapping = currentMethod.getDeclaredAnnotation(RequestMapping.class);
                    requestMethods = requestMapping.method()[0];
                    String[] value = requestMapping.value();
                    requestUrl = requestUrl + StringUtils.join(value);
                } else if (currentMethod.isAnnotationPresent(PostMapping.class)) {
                    PostMapping postMapping = currentMethod.getDeclaredAnnotation(PostMapping.class);
                    requestMethods = RequestMethod.POST;
                    String[] value = postMapping.value();
                    requestUrl = requestUrl + StringUtils.join(value);
                } else if (currentMethod.isAnnotationPresent(GetMapping.class)) {
                    GetMapping getMapping = currentMethod.getDeclaredAnnotation(GetMapping.class);
                    requestMethods = RequestMethod.GET;
                    String[] value = getMapping.value();
                    requestUrl = requestUrl + StringUtils.join(value);
                } else if (currentMethod.isAnnotationPresent(DeleteMapping.class)) {
                    DeleteMapping deleteMapping = currentMethod.getDeclaredAnnotation(DeleteMapping.class);
                    requestMethods = RequestMethod.DELETE;
                    String[] value = deleteMapping.value();
                    requestUrl = requestUrl + StringUtils.join(value);
                } else if (currentMethod.isAnnotationPresent(PutMapping.class)) {
                    PutMapping putMapping = currentMethod.getDeclaredAnnotation(PutMapping.class);
                    requestMethods = RequestMethod.PUT;
                    String[] value = putMapping.value();
                    requestUrl = requestUrl + StringUtils.join(value);
                }

                AspectObject aspectObject = new AspectObject();
                aspectObject.setMethod(requestMethods);
                aspectObject.setUrl(StrUtil.isBlank(contextPath) ? requestUrl : contextPath + requestUrl);
                return aspectObject;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}