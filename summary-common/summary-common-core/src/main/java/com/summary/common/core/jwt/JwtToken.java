package com.summary.common.core.jwt;

import lombok.Data;

/**
 * jwt 中存储的数据
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class JwtToken {
    /**
     * 认证应用
     */
    private String app;
    /**
     * 请求Ip
     */
    private String requestIp;
    /**
     * 管理员id
     */
    private Long adminId;

}
