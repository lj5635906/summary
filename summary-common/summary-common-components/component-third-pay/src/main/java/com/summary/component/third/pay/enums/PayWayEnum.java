package com.summary.component.third.pay.enums;

import com.summary.component.third.pay.constants.AliPayConstants;
import com.summary.component.third.pay.constants.WxPayConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static com.summary.component.third.pay.enums.PayPlatformEnum.*;

/**
 * 支付方式
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {
    /**
     * 支付宝
     * app支付
     */
    ALI_PAY_APP("ALI_PAY_APP", AliPayConstants.TradeType.APP, ALI_PAY, "支付宝app"),
    /**
     * 支付宝
     * 电脑网站支付
     */
    ALI_PAY_PC("ALI_PAY_PC", AliPayConstants.TradeType.PAGE, ALI_PAY, "支付宝pc 电脑网站"),
    /**
     * 支付宝
     * 手机网站支付支付
     */
    ALI_PAY_WAP("ALI_PAY_WAP", AliPayConstants.TradeType.WAP, ALI_PAY, "支付宝-手机网站支付"),
    /**
     * 微信公众账号支付
     * JSAPI支付是指商户通过调用微信支付提供的JSAPI接口，在支付场景中调起微信支付模块完成收款。
     * 应用场景有：
     * 线下场所：调用接口生成二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
     * 公众号场景：用户在微信公众账号内进入商家公众号，打开某个主页面，完成支付
     * PC网站场景：在网站中展示二维码，用户扫描二维码后在微信浏览器中打开页面后完成支付
     */
    WX_PAY_MP("WX_PAY_MP", WxPayConstants.TradeType.JSAPI, WX_PAY, "微信公众账号支付"),
    /**
     * 微信H5支付
     * H5支付是指商户在微信客户端外的移动端网页展示商品或服务，用户在前述页面确认使用微信支付时，商户发起本服务呼起微信客户端进行支付。
     * 主要用于触屏版的手机浏览器请求微信支付的场景。可以方便的从外部浏览器唤起微信支付。
     */
    WX_PAY_WEB("WX_PAY_WEB", WxPayConstants.TradeType.MWEB, WX_PAY, "微信H5支付"),
    /**
     * 微信Native支付
     * Native支付是指商户系统按微信支付协议生成支付二维码，用户再用微信“扫一扫”完成支付的模式。该模式适用于PC网站、实体店单品或订单、媒体广告支付等场景。
     */
    WX_PAY_NATIVE("WX_PAY_NATIVE", WxPayConstants.TradeType.NATIVE, WX_PAY, "微信Native支付"),
    /**
     * 微信小程序支付
     */
    WX_PAY_MINI("WX_PAY_MINI", WxPayConstants.TradeType.JSAPI, WX_PAY, "微信小程序支付"),
    /**
     * 微信APP支付
     * APP支付是指商户通过在移动端应用APP中集成开放SDK调起微信支付模块来完成支付。适用于在移动端APP中集成微信支付功能的场景。
     */
    WX_PAY_APP("WX_PAY_APP", WxPayConstants.TradeType.APP, WX_PAY, "微信APP支付"),
    /**
     * 苹果支付
     */
    APPLE_PAY_APP("APPLE_PAY_APP", "APP", APPLE_PAY, "苹果支付");

    private static final Map<String, PayWayEnum> VALUE_MAP = new HashMap<>();

    static {
        for (PayWayEnum enums : PayWayEnum.values()) {
            VALUE_MAP.put(enums.code, enums);
        }
    }

    public static PayWayEnum getByCode(String code) {
        return VALUE_MAP.get(code);
    }

    private final String code;
    private final String tradeType;

    private final PayPlatformEnum platform;

    private final String desc;

}
