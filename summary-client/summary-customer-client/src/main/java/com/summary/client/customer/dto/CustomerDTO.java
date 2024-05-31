package com.summary.client.customer.dto;

import lombok.Data;

/**
 * 用户信息
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@Data
public class CustomerDTO {

    /**
     * 用户id
     */
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
}
