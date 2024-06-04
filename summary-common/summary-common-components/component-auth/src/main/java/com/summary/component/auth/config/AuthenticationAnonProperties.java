package com.summary.component.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashSet;
import java.util.Set;

/**
 * 鉴权不需要验证 Token 的URL集合
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@RefreshScope
@ConfigurationProperties(prefix = "summary.auth.anon")
public class AuthenticationAnonProperties {

    /**
     * 不需要验证 Token 的URL集合
     */
    private Set<String> path = new HashSet<>();

    public Set<String> getPath() {
        return path;
    }

    public void setPath(Set<String> path) {
        this.path = path;
    }
}
