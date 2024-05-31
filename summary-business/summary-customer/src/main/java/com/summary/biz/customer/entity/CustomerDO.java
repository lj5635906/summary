package com.summary.biz.customer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("customer")
public class CustomerDO extends BaseDO<CustomerDO> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Long customerId;

    /**
     * 电话号码
     */
    private String customerMobile;

    /**
     * 姓名
     */
    private String customerName;

    /**
     * 性别: -1 未知、0-女性、1-男性
     */
    private Integer sex;

    /**
     * unionId
     */
    private String unionId;

    /**
     * 小程序-openid
     */
    private String openId;

    @Override
    public Serializable pkVal() {
        return this.customerId;
    }
}
