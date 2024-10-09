package com.summary.biz.order.service.pay;

import com.summary.biz.order.entity.OrderDO;
import com.summary.biz.order.entity.OrderPaymentDO;
import com.summary.biz.order.entity.OrderPaymentItemDO;
import com.summary.biz.order.service.OrderPaymentItemService;
import com.summary.biz.order.service.OrderPaymentService;
import com.summary.biz.order.service.OrderService;
import com.summary.client.order.enums.OrderStateEnum;
import com.summary.client.order.enums.PayStateEnum;
import com.summary.client.order.param.pay.OrderPayParam;
import com.summary.common.core.enums.ClientTypeEnum;
import com.summary.common.core.utils.Assert;
import com.summary.common.core.utils.DateTimeUtils;
import com.summary.component.generator.id.snowflake.IdWorker;
import com.summary.component.third.pay.enums.PayPlatformEnum;
import com.summary.component.third.pay.enums.PayWayEnum;

import java.time.LocalDateTime;

import static com.summary.client.order.code.OrderExceptionCode.order_no_exist;
import static com.summary.client.order.code.OrderExceptionCode.order_payed;
import static com.summary.common.core.constant.GlobalConstant.StrConstant.EMPTY_STRING;

/**
 * @author jie.luo
 * @since 2024/7/22
 */
public abstract class AbstractPayService {

    private final OrderService orderService;
    private final OrderPaymentService orderPaymentService;
    private final OrderPaymentItemService orderPaymentItemService;

    public AbstractPayService(OrderService orderService, OrderPaymentService orderPaymentService, OrderPaymentItemService orderPaymentItemService) {
        this.orderService = orderService;
        this.orderPaymentService = orderPaymentService;
        this.orderPaymentItemService = orderPaymentItemService;
    }

    /**
     * 订单检测逻辑
     *
     * @param orderId .
     * @return .
     */
    protected OrderDO check(Long orderId) {
        // 订单check
        OrderDO order = orderService.getById(orderId);
        Assert.isNull(order, order_no_exist);

        // 判断订单是否已支付
        Assert.isTrue(!OrderStateEnum.WAIT_PAY.getCode().equals(order.getOrderState()), order_payed);

        return order;
    }

    /**
     * 处理0元购逻辑
     *
     * @param order .
     */
    protected String doFreePay(OrderDO order, PayWayEnum payWay, OrderPayParam param) {

        ClientTypeEnum clientTypeEnum = ClientTypeEnum.getByCode(param.getClientType());
        LocalDateTime now = DateTimeUtils.getNow();
        Long paymentId = IdWorker.nextId();
        // 发起支付，写入支付信息数据
        OrderPaymentDO orderPaymentDO = OrderPaymentDO.builder()
                .paymentId(paymentId)
                .orderId(order.getOrderId())
                .payClientType(clientTypeEnum.getCode())
                .payPlatform(PayPlatformEnum.FREE.name())
                .payWay(payWay.getCode())
                .payState(PayStateEnum.payed.getCode())
                .startPayTime(now)
                .payTime(now)
                .startRefundTime(now)
                .refundTime(now)
                .paymentItemId(0L)
                .build();
        orderPaymentService.save(orderPaymentDO);

        OrderDO modify = new OrderDO();
        modify.setOrderId(order.getOrderId());
        modify.setPayTime(now);
        modify.setOrderState(OrderStateEnum.PAY.getCode());
        modify.setOrderStateDesc(OrderStateEnum.PAY.getMessage());
        orderService.updateById(modify);

        return EMPTY_STRING;
    }

    /**
     * 具体 发起支付逻辑
     *
     * @param order  .
     * @param payWay .
     * @param param  .
     * @return .
     */
    protected String doPay(OrderDO order, PayWayEnum payWay, OrderPayParam param) {

        // 调用三方支付
        String body = doThirdPay(param, order, payWay);

        // 客户端类型
        ClientTypeEnum clientTypeEnum = ClientTypeEnum.getByCode(param.getClientType());
        // 当前时间
        LocalDateTime now = DateTimeUtils.getNow();
        // 支付信息id
        Long paymentId = IdWorker.nextId();
        // 订单-支付子表id
        Long paymentItemId = IdWorker.nextId();

        // 发起支付，写入支付信息数据
        OrderPaymentDO orderPaymentDO = OrderPaymentDO.builder()
                .paymentId(paymentId)
                .orderId(order.getOrderId())
                .payClientType(clientTypeEnum.getCode())
                .payPlatform(PayPlatformEnum.ALI_PAY.name())
                .payWay(payWay.getCode())
                .payState(PayStateEnum.paying.getCode())
                .startPayTime(now)
                .payTime(now)
                .startRefundTime(now)
                .refundTime(now)
                .paymentItemId(paymentItemId)
                .build();
        orderPaymentService.save(orderPaymentDO);

        OrderPaymentItemDO orderPaymentItemDO = OrderPaymentItemDO.builder()
                .paymentItemId(paymentItemId)
                .payWay(payWay.getCode())
                .payInfo(body)
                .orderId(order.getOrderId())
                .paymentId(paymentId)
                .appId(EMPTY_STRING)
                .mchId(EMPTY_STRING)
                .build();
        orderPaymentItemService.save(orderPaymentItemDO);

        return body;
    }

    /**
     * 发起支付流程
     *
     * @param param  .
     * @param payWay .
     */
    protected String pay(OrderPayParam param, PayWayEnum payWay) {

        // check
        OrderDO order = check(param.getOrderId());

        // 判断订单是否为0元支付
        if (order.getPayMoney().compareTo(0L) == 0) {
            return doFreePay(order, payWay, param);
        } else {
            return doPay(order, payWay, param);
        }

    }

    /**
     * 具体调用第三方发起支付方法
     *
     * @param param  {@link OrderPayParam}
     * @param order  .
     * @param payWay .
     * @return 发起支付后数据
     */
    protected abstract String doThirdPay(OrderPayParam param, OrderDO order, PayWayEnum payWay);

    /**
     * 微信 获取 交易结束时间.
     *
     * @return .
     */
    protected String getExpireTimeToWxPay() {
        LocalDateTime localDateTime = DateTimeUtils.getNow().plusMinutes(10);
        return DateTimeUtils.convertLocalDateTimeToString(localDateTime, "yyyy-MM-DDTHH:mm:ss") + "+08:00";
    }

    /**
     * 支付宝 获取 交易结束时间.
     *
     * @return .
     */
    protected String getExpireTimeToWxAliPay() {
        LocalDateTime localDateTime = DateTimeUtils.getNow().plusMinutes(10);
        return DateTimeUtils.convertLocalDateTimeToString(localDateTime, "yyyy-MM-DD HH:mm:ss");
    }
}
