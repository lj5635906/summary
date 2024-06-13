package com.summary.component.logs.config;

import com.summary.component.logs.LogType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Data
@ConfigurationProperties(prefix = "summary.log")
public class LogProperties {

    /**
     * 当前服务是否为web容器
     */
    private Boolean webFlag = false;

    private LogType type = LogType.METHOD;

}
