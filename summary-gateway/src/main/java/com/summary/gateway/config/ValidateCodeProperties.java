package com.summary.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 需要验证验证码的 url集合
 *
 * @author jie.luo
 * @since 2024/6/6
 */
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "summary.code.check")
public class ValidateCodeProperties {

    private List<String> paths = new ArrayList<>();

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }
}
