package com.summary.biz.order.service.pay;

import com.alibaba.fastjson.JSONObject;
import com.summary.biz.order.entity.OrderDO;
import com.summary.biz.order.service.OrderPaymentItemService;
import com.summary.biz.order.service.OrderPaymentService;
import com.summary.biz.order.service.OrderService;
import com.summary.client.order.param.pay.OrderPayParam;
import com.summary.component.third.pay.enums.PayWayEnum;
import com.summary.component.third.pay.model.wx.WxPayParam;
import com.summary.component.third.pay.model.wx.result.WxPayJsapiOrderResponse;
import com.summary.component.third.pay.service.ThirdPayService;
import org.springframework.stereotype.Component;

/**
 * 微信 公众号、小程序 发起支付
 *
 * @author jie.luo
 * @since 2024/7/22
 */
@Component
public class WxJsApiPayService extends AbstractPayService {

    private final ThirdPayService thirdPayService;

    public WxJsApiPayService(ThirdPayService thirdPayService, OrderService orderService, OrderPaymentService orderPaymentService, OrderPaymentItemService orderPaymentItemService) {
        super(orderService, orderPaymentService, orderPaymentItemService);
        this.thirdPayService = thirdPayService;
    }

    @Override
    public String pay(OrderPayParam param, PayWayEnum payWay) {
        return super.pay(param, payWay);
    }

    @Override
    protected String doThirdPay(OrderPayParam param, OrderDO order, PayWayEnum payWay) {
        WxPayParam payParam = new WxPayParam();

        payParam.setPayWayEnum(payWay);
        payParam.setOutTradeNo(String.valueOf(order.getOrderId()));

        payParam.setAmount(Math.toIntExact(order.getPayMoney()));

        payParam.setBody("order");
        payParam.setTimeExpire(super.getExpireTimeToWxPay());

        payParam.setAppId(param.getAppId());
        // TODO 获取openId
        // Long userId = TokenManager.getUserId();`
        payParam.setOpenId("o6_bmjrPTmbxmlbTqY7FeGOc6XTg");
        payParam.setAttach(payWay.getCode());

        // 下单
        WxPayJsapiOrderResponse response = thirdPayService.pay(payParam);

        return JSONObject.toJSONString(response);
    }
}
