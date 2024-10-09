package com.summary.biz.order.provider;

import com.summary.biz.order.service.pay.*;
import com.summary.client.order.param.pay.OrderPayAliParam;
import com.summary.client.order.param.pay.OrderPayParam;
import com.summary.client.remote.PayRemoteService;
import com.summary.component.third.pay.enums.PayWayEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付相关接口
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@RestController
@RequestMapping("/order")
public class PayRemoteServiceProvider implements PayRemoteService {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private WxJsApiPayService wxJsApiPayService;
    @Autowired
    private WxNativePayService wxNativePayService;
    @Autowired
    private WxH5PayService wxH5PayService;
    @Autowired
    private WxAppPayService wxAppPayService;

    /**
     * 发起支付-支付宝-app支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/ali/app")
    public String orderPayAliApp(@Valid @RequestBody OrderPayAliParam param) {
        return aliPayService.pay(param, PayWayEnum.ALI_PAY_APP);
    }

    /**
     * 发起支付-支付宝-电脑网站支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/ali/pc")
    public String orderPayAliPc(@Valid @RequestBody OrderPayAliParam param) {
        return aliPayService.pay(param, PayWayEnum.ALI_PAY_PC);
    }

    /**
     * 发起支付-支付宝-手机网站支付支付
     *
     * @param param {@link OrderPayAliParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/ali/wap")
    public String orderPayAliWap(@Valid @RequestBody OrderPayAliParam param) {
        return aliPayService.pay(param, PayWayEnum.ALI_PAY_WAP);
    }

    /**
     * 发起支付-微信APP支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/wx/app")
    public String orderPayWxApp(@RequestBody OrderPayParam param) {
        return wxAppPayService.pay(param, PayWayEnum.WX_PAY_APP);
    }

    /**
     * 发起支付-微信小程序支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/wx/applet")
    public String orderPayWxApplet(@RequestBody OrderPayParam param) {
        return wxJsApiPayService.pay(param, PayWayEnum.WX_PAY_APPLET);
    }

    /**
     * 发起支付-公众号支付
     *
     * @param param {@link OrderPayParam}
     * @return 支付响应数据
     */
    @Override
    @PostMapping("/pay/wx/mp")
    public String orderPayWxMp(@RequestBody OrderPayParam param) {
        return wxJsApiPayService.pay(param, PayWayEnum.WX_PAY_MP);
    }

    /**
     * 发起支付-微信H5支付
     *
     * @param param {@link OrderPayParam}
     * @return h5支付页面
     */
    @Override
    @PostMapping("/pay/wx/h5")
    public String orderPayWxH5(@RequestBody OrderPayParam param) {
        return wxH5PayService.pay(param, PayWayEnum.WX_PAY_H5);
    }

    /**
     * 发起支付-微信Native支付
     *
     * @param param {@link OrderPayParam}
     * @return 二维码
     */
    @Override
    @PostMapping("/pay/wx/native")
    public String orderPayWxNative(@RequestBody OrderPayParam param) {
        return wxNativePayService.pay(param, PayWayEnum.WX_PAY_NATIVE);
    }
}
