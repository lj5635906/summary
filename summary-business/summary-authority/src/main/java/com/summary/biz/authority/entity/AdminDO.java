package com.summary.biz.authority.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 管理员表
 *
 * @author myabtis-plus
 * @since 2024-05-29
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("auth_admin")
public class AdminDO extends BaseDO<AdminDO> {

    private static final long serialVersionUID = 1L;
    /*** 管理员id */
    @TableId(value = "admin_id", type = IdType.AUTO)
    private Long adminId;
    /*** 用户名 */
    private String username;
    /*** 密码 */
    private String password;
    /*** 真实姓名 */
    private String realName;
    /*** 电话 */
    private String mobile;
    /*** 邮箱 */
    private String email;
    /*** 性别:0-女、1-男 */
    private Integer sex;
    /*** 用户状态: 1-锁定、2-正常 、3-注销 */
    private Integer userStatus;
    /*** 账号锁定时间 */
    private LocalDateTime lockedTime;

    @Builder
    public AdminDO(Integer version, LocalDateTime createTime, LocalDateTime updateTime, Boolean deleteFlag, Long adminId, String username, String password, String realName, String mobile, String email, Integer sex, Integer userStatus, LocalDateTime lockedTime) {
        super(version, createTime, updateTime, deleteFlag);
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.mobile = mobile;
        this.email = email;
        this.sex = sex;
        this.userStatus = userStatus;
        this.lockedTime = lockedTime;
    }

    @Override
    public Serializable pkVal() {
        return this.adminId;
    }

}
