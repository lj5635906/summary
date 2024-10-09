package com.summary.component.third.pay.service.ali;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.summary.common.core.exception.CustomException;
import com.summary.component.third.pay.config.AliPayProperties;
import com.summary.component.third.pay.constants.AliPayConstants;
import com.summary.component.third.pay.model.ali.AliPayParam;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Map;

import static com.summary.common.core.exception.code.BaseExceptionCode.PAY_WAY_NOT_SUPPORT;
import static com.summary.component.third.pay.constants.AliPayConstants.*;

/**
 * @author jie.luo
 * @since 2024/7/20
 */
@Slf4j
@Builder
public class AliPayService {

    private final AliPayProperties aliPayProperties;

    /**
     * 加载支付宝支付相关配置
     *
     * @param aliPayProperties AliPayProperties
     */
    public static void loadAliPayOptions(AliPayProperties aliPayProperties) {
        Config config = new Config();
        config.protocol = PROTOCOL;
        config.gatewayHost = GATEWAY_HOST;
        config.signType = SIGN_TYPE;
        config.appId = aliPayProperties.getAppId();
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = aliPayProperties.getPrivateKey();
        // 注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = aliPayProperties.getAliPayPublicKey();
        // 可设置异步通知接收服务地址（可选）
        config.notifyUrl = aliPayProperties.getNotifyUrl();

        // 设置参数（全局只需设置一次）
        Factory.setOptions(config);
    }


    /**
     * 支付宝支付创建订单
     *
     * @param payParam AliPayParam
     * @param <T>      BaseWxPayResult
     * @return BaseWxPayResult
     * @throws Exception .
     */
    public static <T> T createOrder(AliPayParam payParam) throws Exception {

        switch (payParam.getPayWayEnum().getTradeType()) {

            case AliPayConstants.TradeType.APP: {
                AlipayTradeAppPayResponse response = Factory
                        .Payment
                        .App()
                        .optional("time_expire",payParam.getTimeExpire())
                        .optional("passback_params",payParam.getAttach())
                        .pay(payParam.getBody(), payParam.getOutTradeNo(), fenToYuan(payParam.getAmount()));
                return (T) response;
            }

            case AliPayConstants.TradeType.PAGE: {
                AlipayTradePagePayResponse response = Factory
                        .Payment
                        .Page()
                        .optional("time_expire",payParam.getTimeExpire())
                        .optional("passback_params",payParam.getAttach())
                        .pay(payParam.getBody(), payParam.getOutTradeNo(), fenToYuan(payParam.getAmount()), payParam.getReturnUrl());
                return (T) response;
            }

            case AliPayConstants.TradeType.WAP: {
                AlipayTradeWapPayResponse response = Factory
                        .Payment
                        .Wap()
                        .optional("time_expire",payParam.getTimeExpire())
                        .optional("passback_params",payParam.getAttach())
                        .pay(payParam.getBody(), payParam.getOutTradeNo(), fenToYuan(payParam.getAmount()), payParam.getQuitUrl(), payParam.getReturnUrl());
                return (T) response;
            }

            default: {
                throw new CustomException(PAY_WAY_NOT_SUPPORT);
            }
        }

    }

    /**
     * 验证支付宝支付回调是否正确
     *
     * @param parameters 支付宝回调参数
     * @return 验证签名是否成功
     */
    public static boolean verify(Map<String, String> parameters) {

        // 验证签名是否修改
        try {
            return Factory
                    .Payment
                    .Common()
                    .verifyNotify(parameters);
        } catch (Exception e) {
            log.error("支付宝回调验签出现异常", e);
            return false;
        }
    }

    /**
     * 支付宝退款
     *
     * @param outTradeNo   商户订单号
     * @param refundAmount 退款金额(单位:分)
     * @return {@link AlipayTradeRefundResponse}
     */
    public static AlipayTradeRefundResponse refund(String outTradeNo, Integer refundAmount) throws Exception {
        return Factory.Payment.Common().refund(outTradeNo, fenToYuan(refundAmount));
    }

    /**
     * 支付宝退款查询
     *
     * @param outTradeNo   商户订单号
     * @param outRequestNo 退款金额(单位:分)
     * @return {@link AlipayTradeFastpayRefundQueryResponse}
     */
    public static AlipayTradeFastpayRefundQueryResponse refundQuery(String outTradeNo, String outRequestNo) throws Exception {
        return Factory.Payment.Common().queryRefund(outTradeNo, outRequestNo);
    }

    /**
     * 支付宝关闭交易
     *
     * @param outTradeNo 商户订单号
     * @return {@link AlipayTradeCloseResponse}
     * @throws Exception .
     */
    public static AlipayTradeCloseResponse orderClose(String outTradeNo) throws Exception {
        return Factory.Payment.Common().close(outTradeNo);
    }

    /**
     * 交易查询
     *
     * @param outTradeNo 商户订单号
     * @return {@link AlipayTradeQueryResponse}
     * @throws Exception .
     */
    public static AlipayTradeQueryResponse orderQuery(String outTradeNo) throws Exception {
        return Factory.Payment.Common().query(outTradeNo);
    }

    /**
     * 将单位分转换成单位元.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the string
     */
    public static String fenToYuan(Integer fen) {
        if (null == fen) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(fen) / 100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }
}
