package com.summary.component.third.pay.model;

import com.summary.component.third.pay.enums.PayWayEnum;
import lombok.Data;

/**
 * 支付时 通用 请求参数
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
public class PayParam {
    /**
     * 支付方式
     */
    private PayWayEnum payWayEnum;
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 订单金额（单位：分）
     */
    private Integer amount;
    /**
     * 请求Ip
     */
    private String requestIp;
    /**
     * 支付异步回掉地址，通知url必须为直接可访问的url，不能携带参数.
     */
    private String notifyUrl;
    /**
     * 商品描述/订单标题
     */
    private String body;
    /**
     * 交易结束时间.
     */
    private String timeExpire;
    /**
     * 附加数据
     */
    private String attach;
}
