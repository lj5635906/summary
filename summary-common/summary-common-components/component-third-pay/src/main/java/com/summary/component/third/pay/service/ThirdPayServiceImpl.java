package com.summary.component.third.pay.service;

import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.summary.common.core.exception.CustomException;
import com.summary.component.third.pay.config.WxPayProperties;
import com.summary.component.third.pay.constants.AliPayConstants;
import com.summary.component.third.pay.constants.WxPayConstants;
import com.summary.component.third.pay.enums.PayPlatformEnum;
import com.summary.component.third.pay.model.PayParam;
import com.summary.component.third.pay.model.ali.AliPayParam;
import com.summary.component.third.pay.model.ali.AliPayResponse;
import com.summary.component.third.pay.model.wx.WxPayParam;
import com.summary.component.third.pay.model.wx.result.WxPayAppOrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayH5OrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayJsapiOrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayNativeOrderResponse;
import com.summary.component.third.pay.service.ali.AliPayService;
import com.summary.component.third.pay.service.wx.WxPayService;
import lombok.extern.slf4j.Slf4j;

import static com.summary.common.core.exception.code.BaseExceptionCode.PAY_FAIL;
import static com.summary.common.core.exception.code.BaseExceptionCode.PAY_WAY_NOT_SUPPORT;

/**
 * 支付实现
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Slf4j
public class ThirdPayServiceImpl implements ThirdPayService {

    private WxPayService wxPayService;

    public ThirdPayServiceImpl(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    public ThirdPayServiceImpl() {
    }

    @Override
    public <T> T pay(PayParam payParam) {
        try {
            // 微信支付
            if (PayPlatformEnum.WX_PAY == payParam.getPayWayEnum().getPlatform()) {

                switch (payParam.getPayWayEnum().getTradeType()) {
                    case WxPayConstants.TradeType.H5: {
                        WxPayH5OrderResponse response = wxPayService.createOrder((WxPayParam) payParam);
                        return (T) response;
                    }

                    case WxPayConstants.TradeType.NATIVE: {
                        WxPayNativeOrderResponse response = wxPayService.createOrder((WxPayParam) payParam);
                        return (T) response;
                    }

                    case WxPayConstants.TradeType.APP: {
                        WxPayAppOrderResponse response = wxPayService.createOrder((WxPayParam) payParam);
                        return (T) response;
                    }

                    case WxPayConstants.TradeType.JSAPI: {
                        WxPayJsapiOrderResponse response = wxPayService.createOrder((WxPayParam) payParam);
                        return (T) response;
                    }
                    default: {
                        throw new CustomException(PAY_WAY_NOT_SUPPORT);
                    }
                }
            }
            // 支付宝支付
            else if (PayPlatformEnum.ALI_PAY == payParam.getPayWayEnum().getPlatform()) {
                switch (payParam.getPayWayEnum().getTradeType()) {
                    case AliPayConstants.TradeType.APP: {
                        AlipayTradeAppPayResponse result = AliPayService.createOrder((AliPayParam) payParam);
                        AliPayResponse response = new AliPayResponse(result.body);
                        return (T) response;
                    }

                    case AliPayConstants.TradeType.PAGE: {
                        AlipayTradePagePayResponse result = AliPayService.createOrder((AliPayParam) payParam);
                        AliPayResponse response = new AliPayResponse(result.body);
                        return (T) response;
                    }

                    case AliPayConstants.TradeType.WAP: {
                        AlipayTradeWapPayResponse result = AliPayService.createOrder((AliPayParam) payParam);
                        AliPayResponse response = new AliPayResponse(result.body);
                        return (T) response;
                    }

                    default: {
                        throw new CustomException(PAY_WAY_NOT_SUPPORT);
                    }
                }
            }

            throw new CustomException(PAY_WAY_NOT_SUPPORT);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("下单出现异常", e);
            throw new CustomException(PAY_FAIL);
        }
    }
}
