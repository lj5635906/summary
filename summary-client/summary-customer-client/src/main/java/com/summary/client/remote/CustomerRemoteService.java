package com.summary.client.remote;

import com.summary.client.customer.dto.CustomerDTO;

/**
 * 用户相关接口
 *
 * @author jie.luo
 * @since 2024/5/31
 */
public interface CustomerRemoteService {

    /**
     * 用户-获取详情
     *
     * @param customerId 用户id
     * @return 详情
     */
    CustomerDTO getCustomerByCustomerId(Long customerId);

    /**
     * 用户-获取详情
     *
     * @param mobile 电话
     * @return 详情
     */
    CustomerDTO getCustomerByMobile(String mobile);

    /**
     * 用户-获取详情
     *
     * @param unionId unionId
     * @return 详情
     */
    CustomerDTO getCustomerByUnionId(String unionId);

}
