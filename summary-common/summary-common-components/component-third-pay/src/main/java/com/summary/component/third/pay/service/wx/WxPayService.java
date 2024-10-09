package com.summary.component.third.pay.service.wx;

import cn.hutool.core.util.StrUtil;
import com.summary.common.core.exception.CustomException;
import com.summary.component.third.pay.config.WxPayProperties;
import com.summary.component.third.pay.constants.WxPayConstants;
import com.summary.component.third.pay.model.wx.WxPayParam;
import com.summary.component.third.pay.model.wx.result.WxPayAppOrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayH5OrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayJsapiOrderResponse;
import com.summary.component.third.pay.model.wx.result.WxPayNativeOrderResponse;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.service.payments.app.AppServiceExtension;
import com.wechat.pay.java.service.payments.h5.H5Service;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import lombok.extern.slf4j.Slf4j;

import static com.summary.common.core.exception.code.BaseExceptionCode.PAY_WAY_NOT_SUPPORT;


/**
 * 微信支付相关实现
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Slf4j
public class WxPayService {

    private Config config;

    private WxPayProperties wxPayProperties;

    public WxPayService(Config config, WxPayProperties wxPayProperties) {
        this.config = config;
        this.wxPayProperties = wxPayProperties;
    }

    /**
     * 微信支付-预支付
     *
     * @param payParam AliPayParam
     * @param <T>      BaseWxPayResult
     * @return BaseWxPayResult
     * @throws Exception .
     */
    public <T> T unifiedOrder(WxPayParam payParam) {

        switch (payParam.getPayWayEnum().getTradeType()) {
            // APP 下单
            case WxPayConstants.TradeType.APP: {
                AppServiceExtension service = new AppServiceExtension.Builder().config(config).build();

                com.wechat.pay.java.service.payments.app.model.PrepayRequest request = new com.wechat.pay.java.service.payments.app.model.PrepayRequest();
                com.wechat.pay.java.service.payments.app.model.Amount amount = new com.wechat.pay.java.service.payments.app.model.Amount();

                amount.setTotal(payParam.getAmount());
                request.setAmount(amount);
                request.setAppid(payParam.getAppId());
                request.setMchid(wxPayProperties.getMchId());
                request.setDescription(payParam.getBody());
                request.setNotifyUrl(StrUtil.isBlank(payParam.getNotifyUrl()) ? wxPayProperties.getNotifyUrl() : payParam.getNotifyUrl());
                request.setOutTradeNo(payParam.getOutTradeNo());
                request.setTimeExpire(payParam.getTimeExpire());
                request.setAttach(payParam.getAttach());

                com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(request);

                return (T) response;
            }
            // Native下单
            case WxPayConstants.TradeType.NATIVE: {
                NativePayService service = new NativePayService.Builder().config(config).build();

                com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest request = new com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest();
                com.wechat.pay.java.service.payments.nativepay.model.Amount amount = new com.wechat.pay.java.service.payments.nativepay.model.Amount();

                amount.setTotal(payParam.getAmount());
                request.setAmount(amount);
                request.setAppid(payParam.getAppId());
                request.setMchid(wxPayProperties.getMchId());
                request.setDescription(payParam.getBody());
                request.setNotifyUrl(StrUtil.isBlank(payParam.getNotifyUrl()) ? wxPayProperties.getNotifyUrl() : payParam.getNotifyUrl());
                request.setOutTradeNo(payParam.getOutTradeNo());
                request.setTimeExpire(payParam.getTimeExpire());
                request.setAttach(payParam.getAttach());

                com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse prepay = service.prepay(request);

                return (T) prepay;
            }
            // H5下单
            case WxPayConstants.TradeType.H5: {
                H5Service service = new H5Service.Builder().config(config).build();
                com.wechat.pay.java.service.payments.h5.model.PrepayRequest request = new com.wechat.pay.java.service.payments.h5.model.PrepayRequest();
                com.wechat.pay.java.service.payments.h5.model.Amount amount = new com.wechat.pay.java.service.payments.h5.model.Amount();

                amount.setTotal(payParam.getAmount());
                request.setAmount(amount);
                request.setAppid(payParam.getAppId());
                request.setMchid(wxPayProperties.getMchId());
                request.setDescription(payParam.getBody());
                request.setNotifyUrl(StrUtil.isBlank(payParam.getNotifyUrl()) ? wxPayProperties.getNotifyUrl() : payParam.getNotifyUrl());
                request.setOutTradeNo(payParam.getOutTradeNo());
                request.setTimeExpire(payParam.getTimeExpire());
                request.setAttach(payParam.getAttach());

                com.wechat.pay.java.service.payments.h5.model.PrepayResponse prepay = service.prepay(request);

                return (T) prepay;
            }
            // JSAPI下单、小程序下单
            case WxPayConstants.TradeType.JSAPI: {
                JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).build();

                com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest request = new com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest();
                com.wechat.pay.java.service.payments.jsapi.model.Amount amount = new com.wechat.pay.java.service.payments.jsapi.model.Amount();
                amount.setTotal(payParam.getAmount());
                request.setAmount(amount);
                request.setAppid(payParam.getAppId());
                request.setMchid(wxPayProperties.getMchId());
                request.setDescription(payParam.getBody());
                request.setNotifyUrl(StrUtil.isBlank(payParam.getNotifyUrl()) ? wxPayProperties.getNotifyUrl() : payParam.getNotifyUrl());
                request.setOutTradeNo(payParam.getOutTradeNo());
                request.setTimeExpire(payParam.getTimeExpire());
                request.setAttach(payParam.getAttach());

                Payer payer = new Payer();
                payer.setOpenid(payParam.getOpenId());
                request.setPayer(payer);

                com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(request);

                return (T) response;
            }

            default: {
                throw new CustomException(PAY_WAY_NOT_SUPPORT);
            }
        }
    }

    /**
     * 微信支付创建订单
     *
     * @param wxPayParam WxPayParam
     * @param <T>        BaseWxPayResult
     * @return BaseWxPayResult
     */
    public <T> T createOrder(WxPayParam wxPayParam) {

        switch (wxPayParam.getPayWayEnum().getTradeType()) {

            case WxPayConstants.TradeType.APP: {
                com.wechat.pay.java.service.payments.app.model.PrepayWithRequestPaymentResponse prepay = this.unifiedOrder(wxPayParam);

                final WxPayAppOrderResponse response = WxPayAppOrderResponse.builder()
                        .appId(prepay.getAppid())
                        .partnerId(prepay.getPartnerId())
                        .prepayId(prepay.getPrepayId())
                        .packageVal(prepay.getPackageVal())
                        .nonceStr(prepay.getNonceStr())
                        .timeStamp(prepay.getTimestamp())
                        .sign(prepay.getSign())
                        .build();
                return (T) response;
            }

            case WxPayConstants.TradeType.NATIVE: {
                com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse prepay = this.unifiedOrder(wxPayParam);
                return (T) new WxPayNativeOrderResponse(prepay.getCodeUrl());
            }

            case WxPayConstants.TradeType.H5: {
                com.wechat.pay.java.service.payments.h5.model.PrepayResponse prepay = this.unifiedOrder(wxPayParam);
                return (T) new WxPayH5OrderResponse(prepay.getH5Url());
            }

            case WxPayConstants.TradeType.JSAPI: {
                com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse prepay = this.unifiedOrder(wxPayParam);

                WxPayJsapiOrderResponse response = WxPayJsapiOrderResponse.builder()
                        .appId(prepay.getAppId())
                        .timeStamp(prepay.getTimeStamp())
                        .nonceStr(prepay.getNonceStr())
                        .packageVal(prepay.getPackageVal())
                        .signType(prepay.getSignType())
                        .paySign(prepay.getPaySign())
                        .build();

                return (T) response;
            }

            default: {
                throw new CustomException(PAY_WAY_NOT_SUPPORT);
            }
        }

    }


}
