package com.summary.biz.search.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

/**
 * elasticsearch properties
 *
 * @author jie.luo
 * @since 2024/6/22
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticsearchProperties {
    /**
     * host
     * http://localhost:9200
     */
    private Set<String> hosts;
    /**
     * username
     */
    private String username;
    /**
     * password
     */
    private String password;

}
