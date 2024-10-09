package com.summary.client.remote;

import com.summary.client.order.param.pay.OrderPayAliParam;
import com.summary.client.order.param.pay.OrderPayParam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 支付相关接口
 *
 * @author jie.luo
 * @since 2024/7/22
 */
@FeignClient(value = "summary-order", path = "/order")
public interface PayRemoteService {

    /**
     * 支付宝-app支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/ali/app")
    String orderPayAliApp(@Valid @RequestBody OrderPayAliParam param);

    /**
     * 支付宝-电脑网站支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/ali/pc")
    String orderPayAliPc(@Valid @RequestBody OrderPayAliParam param);

    /**
     * 支付宝-手机网站支付支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/ali/wap")
    String orderPayAliWap(@Valid @RequestBody OrderPayAliParam param);

    /**
     * 微信APP支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/wx/app")
    String orderPayWxApp(@RequestBody OrderPayParam param);

    /**
     * 发起支付-微信小程序支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/wx/applet")
    String orderPayWxApplet(@RequestBody OrderPayParam param);

    /**
     * 发起支付-公众号支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @PostMapping("/pay/wx/mp")
    String orderPayWxMp(@RequestBody OrderPayParam param);

    /**
     * 微信H5支付
     *
     * @param param {@link OrderPayParam}
     * @return h5支付页面
     */
    @PostMapping("/pay/wx/h5")
    String orderPayWxH5(@RequestBody OrderPayParam param);

    /**
     * 微信Native支付
     *
     * @param param {@link OrderPayParam}
     * @return 二维码
     */
    @PostMapping("/pay/wx/native")
    String orderPayWxNative(@RequestBody OrderPayParam param);
}
