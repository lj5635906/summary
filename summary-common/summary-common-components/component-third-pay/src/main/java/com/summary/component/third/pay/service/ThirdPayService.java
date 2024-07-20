package com.summary.component.third.pay.service;

import com.summary.component.third.pay.model.*;

/**
 * 支付相关接口
 *
 * @author jie.luo
 * @since 2024/7/20
 */
public interface ThirdPayService {

    /**
     * 发起支付
     *
     * @param payParam {@link PayParam}
     * @return .
     */
    <T> T pay(PayParam payParam);

}
