package com.summary.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 服务器开启维护配置
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "summary.server.maintain")
public class ServerMaintainConfig {
    /**
     * 系统维护开启标志
     */
    public Boolean openFlag = false;

    public Boolean getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(Boolean openFlag) {
        this.openFlag = openFlag;
    }
}
