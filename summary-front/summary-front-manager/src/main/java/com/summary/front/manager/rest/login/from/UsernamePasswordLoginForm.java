package com.summary.front.manager.rest.login.from;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 用户名密码登陆请求参数
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class UsernamePasswordLoginForm {
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不合法")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不合法")
    private String password;
}
