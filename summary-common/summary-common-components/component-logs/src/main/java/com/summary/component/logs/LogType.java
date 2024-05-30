package com.summary.component.logs;

import java.util.HashMap;
import java.util.Map;

/**
 * 打印日志类型
 * @author jie.luo
 * @since 2024/5/29
 */
public enum LogType {
    /**
     * 请求地址：【/demo-context/demo/demo-get】，请求方法：【GET】
     */
    URL,
    /**
     * 请求方法：【com.summary.business.demo.rest.DemoRest.demoGet()】
     */
    METHOD;

    private static Map<String, LogType> logTypeMap = new HashMap<>(5);

    static {
        for (LogType value : LogType.values()) {
            logTypeMap.put(value.name(), value);
        }
    }

    public static LogType getLogTypeByName(String name) {
        LogType logType = logTypeMap.get(name);
        return null != logType ? logType : LogType.METHOD;
    }
}
