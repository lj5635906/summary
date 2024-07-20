package com.summary.component.third.pay.model.wx.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信公众号支付进行统一下单后组装所需参数的类
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxPayJsapiOrderResponse implements Serializable {

    /**
     * 商户申请的公众号对应的AppID，由微信支付生成，可在公众号后台查看
     */
    private String appId;
    /**
     * 时间戳，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。
     */
    private String timeStamp;
    /**
     * 随机字符串，不长于32位。
     */
    private String nonceStr;
    /**
     * JSAPI下单、小程序下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
     * 由于package为java保留关键字，因此改为packageVal. 前端使用时记得要更改为package
     */
    private String packageVal;
    /**
     * 签名类型，默认为RSA，仅支持RSA。
     */
    private String signType;
    /**
     * 小程序下单：
     * 签名，使用字段appid、timeStamp、nonceStr、package计算得出的签名值
     * 签名所使用的appid，为【小程序下单】时传入的appid，微信支付会校验下单与调起支付所使用的appid的一致性。
     */
    private String paySign;
}
