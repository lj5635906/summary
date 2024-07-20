package com.summary.component.third.pay.model.wx.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信扫码支付统一下单后发起支付拼接所需参数实现类
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxPayNativeOrderResponse implements Serializable {
    /**
     * 【二维码链接】 此URL用于生成支付二维码，然后提供给用户扫码支付。
     * 注意：code_url并非固定值，使用时按照URL格式转成二维码即可。
     */
    private String codeUrl;
}
