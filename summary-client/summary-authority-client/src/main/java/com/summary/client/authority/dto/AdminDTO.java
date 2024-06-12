package com.summary.client.authority.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class AdminDTO implements Serializable {
    /**
     * 管理员id
     */
    private Long adminId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 性别:0-女、1-男
     */
    private Integer sex;
    /**
     * 用户状态: 1-锁定、2-正常 、3-注销
     */
    private Integer userStatus;
    /**
     * 创建时间
     */
    private String createTime;
}
