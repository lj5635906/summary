package com.summary.component.third.pay.model.wx.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信app支付统一下单后发起支付拼接所需参数实现类
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayAppOrderResponse implements Serializable {
    /**
     * 微信开放平台审核通过的移动应用AppID 。
     */
    private String appId;
    /**
     * 请填写商户号mchid对应的值。
     */
    private String partnerId;
    /**
     * 微信返回的支付交易会话ID，该值有效期为2小时。
     */
    private String prepayId;
    /**
     * 暂填写固定值Sign=WXPay
     * 由于package为java保留关键字，因此改为packageVal. 前端使用时记得要更改为package
     */
    private String packageVal;
    /**
     * 随机字符串，不长于32位。推荐随机数生成算法。
     */
    private String nonceStr;
    /**
     * 时间戳，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。
     */
    private String timeStamp;
    /**
     * 签名，使用字段AppID、timeStamp、nonceStr、prepayid计算得出的签名值 注意：取值RSA格式
     */
    private String sign;


}

