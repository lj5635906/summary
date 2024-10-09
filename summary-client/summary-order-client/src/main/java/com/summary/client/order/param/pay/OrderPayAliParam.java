package com.summary.client.order.param.pay;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 订单支付宝支付参数
 *
 * @author jie.luo
 * @since 2024/07/22
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class OrderPayAliParam extends OrderPayParam {
    /**
     * 电脑网站支付、手机网站支付 : 非必填
     * 支付成功后同步跳转的页面，是一个http/https开头的字符串
     */
    private String returnUrl;
    /**
     * 手机网站支付: 必填
     * 用户付款中途退出返回商户网站的地址
     */
    private String quitUrl;
}
