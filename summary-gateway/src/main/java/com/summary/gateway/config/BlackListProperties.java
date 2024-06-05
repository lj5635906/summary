package com.summary.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * 黑名单列表
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "summary.gateway.black")
public class BlackListProperties {

    private Set<String> paths = new HashSet<>();

    public Set<String> getPaths() {
        return paths;
    }

    public void setPaths(Set<String> paths) {
        this.paths = paths;
    }
}
