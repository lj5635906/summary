package com.summary.component.logs.config;

import com.summary.component.logs.LogType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 日志配置类
 * @author jie.luo
 * @since 2024/5/29
 */
@ConfigurationProperties(prefix = "custom.log")
@Data
public class LogProperties {

    private LogType type = LogType.METHOD;

}
