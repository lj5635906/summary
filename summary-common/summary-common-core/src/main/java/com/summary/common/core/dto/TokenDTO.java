package com.summary.common.core.dto;

import lombok.Data;

/**
 * Token
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class TokenDTO {
    /**
     * 访问token
     */
    private String accessToken;
    /**
     * 刷新token
     */
    private String refreshToken;

}
