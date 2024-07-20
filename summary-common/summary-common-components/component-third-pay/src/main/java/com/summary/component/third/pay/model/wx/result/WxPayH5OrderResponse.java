package com.summary.component.third.pay.model.wx.result;

import lombok.*;

import java.io.Serializable;

/**
 * 微信H5支付统一下单后发起支付拼接所需参数实现类.
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayH5OrderResponse implements Serializable {
    /**
     * 【支付跳转链接】 h5_url为拉起微信支付收银台的中间页面，可通过访问该URL来拉起微信客户端，完成支付，h5_url的有效期为5分钟。
     */
    private String h5Url;
}