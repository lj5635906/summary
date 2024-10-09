package com.summary.biz.order.service.pay;

import com.summary.biz.order.entity.OrderDO;
import com.summary.biz.order.service.OrderPaymentItemService;
import com.summary.biz.order.service.OrderPaymentService;
import com.summary.biz.order.service.OrderService;
import com.summary.client.order.param.pay.OrderPayAliParam;
import com.summary.client.order.param.pay.OrderPayParam;
import com.summary.component.third.pay.enums.PayWayEnum;
import com.summary.component.third.pay.model.ali.AliPayParam;
import com.summary.component.third.pay.model.ali.AliPayResponse;
import com.summary.component.third.pay.service.ThirdPayService;
import org.springframework.stereotype.Component;

/**
 * 支付宝：APP、wap（手机网站支付）、page（电脑网站支付）
 *
 * @author jie.luo
 * @since 2024/7/22
 */
@Component
public class AliPayService extends AbstractPayService {

    private final ThirdPayService thirdPayService;

    public AliPayService(ThirdPayService thirdPayService, OrderService orderService, OrderPaymentService orderPaymentService, OrderPaymentItemService orderPaymentItemService) {
        super(orderService, orderPaymentService, orderPaymentItemService);
        this.thirdPayService = thirdPayService;
    }

    public String pay(OrderPayAliParam param, PayWayEnum payWay) {
        return super.pay(param, payWay);
    }

    @Override
    protected String doThirdPay(OrderPayParam param, OrderDO order, PayWayEnum payWay) {

        OrderPayAliParam payAliParam = (OrderPayAliParam) param;

        AliPayParam payParam = new AliPayParam();

        payParam.setReturnUrl(payAliParam.getReturnUrl());
        payParam.setQuitUrl(payAliParam.getQuitUrl());

        payParam.setPayWayEnum(payWay);
        payParam.setOutTradeNo(String.valueOf(order.getOrderId()));

        payParam.setAmount(Math.toIntExact(order.getPayMoney()));

        payParam.setBody("order");
        payParam.setTimeExpire(super.getExpireTimeToWxAliPay());

        // 下单
        AliPayResponse response = thirdPayService.pay(payParam);

        return response.getBody();
    }


}
