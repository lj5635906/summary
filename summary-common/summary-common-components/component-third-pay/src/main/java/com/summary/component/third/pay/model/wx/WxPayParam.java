package com.summary.component.third.pay.model.wx;

import com.summary.component.third.pay.model.PayParam;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 微信支付相关参数
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WxPayParam extends PayParam {
    /**
     * 微信分配的账号ID
     */
    private String appId;
    /**
     * 微信openid, 仅微信公众号/小程序支付时需要
     */
    private String openId;
    /**
     * 附加数据
     */
    private String attach;
}
