package com.summary.client.authority.param;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 修改管理员参数
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Data
public class ModifyAdminParam implements Serializable {

    /**
     * 用户id
     */
    @NotNull(message = "adminId不能为空")
    private Long adminId;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;
    /**
     * 密码不能为空
     */
    @Size(min = 1, message = "密码不能为空")
    private String password;
    /**
     * 真实姓名
     */
    @NotNull(message = "真实姓名不能为空")
    private String realName;
    /**
     * 电话
     */
    @Pattern(regexp = "1\\d{10}", message = "电话号码格式不合法")
    private String mobile;
    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    private String email;
    /**
     * 性别:0-女、1-男
     */
    @NotNull(message = "性别不能为空")
    @Range(message = "性别不合法", min = 0, max = 1)
    private Integer sex;
    /**
     * 用户状态: 1-锁定、2-正常 、3-注销
     */
    @NotNull(message = "用户状态为空")
    @Range(message = "用户状态不合法", min = 1, max = 3)
    private Integer userStatus;
}
