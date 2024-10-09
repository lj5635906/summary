package com.summary.client.order.dto.pay;

import lombok.Data;

/**
 * 微信下单响应参数
 *
 * @author jie.luo
 * @since 2024/07/22
 */
@Data
public class OrderPayWxJsApiDTO {
    /**
     * 应用ID
     */
    private String appId;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 订单详情扩展字符串
     * 由于package为java保留关键字，因此改为 packageVal. 前端使用时记得要更改为package
     */
    private String packageVal;
    /**
     * 签名方式
     */
    private String signType;
    /**
     * 签名
     */
    private String paySign;
}
