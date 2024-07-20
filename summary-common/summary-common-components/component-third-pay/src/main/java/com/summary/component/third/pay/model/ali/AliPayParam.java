package com.summary.component.third.pay.model.ali;

import com.summary.component.third.pay.model.PayParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 支付宝支付相关参数
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AliPayParam extends PayParam {
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
