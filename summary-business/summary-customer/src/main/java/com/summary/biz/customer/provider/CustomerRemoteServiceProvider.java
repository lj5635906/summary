package com.summary.biz.customer.provider;

import com.summary.biz.customer.entity.CustomerDO;
import com.summary.biz.customer.service.CustomerService;
import com.summary.client.customer.dto.CustomerDTO;
import com.summary.client.remote.CustomerRemoteService;
import com.summary.common.core.utils.ConvertUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户相关接口
 *
 * @author jie.luo
 * @since 2024-05-31
 */
@DubboService
public class CustomerRemoteServiceProvider implements CustomerRemoteService {

    @Autowired
    private CustomerService customerService;

    /**
     * 用户-获取详情
     *
     * @param customerId 用户id
     * @return 详情
     */
    @Override
    public CustomerDTO getCustomerByCustomerId(Long customerId) {
        CustomerDO customerDO = customerService.getById(customerId);
        return ConvertUtils.convert(customerDO, CustomerDTO.class);
    }

    /**
     * 用户-获取详情
     *
     * @param mobile 电话
     * @return 详情
     */
    @Override
    public CustomerDTO getCustomerByMobile(String mobile) {
        return customerService.getCustomerByMobile(mobile);
    }

    /**
     * 用户-获取详情
     *
     * @param unionId unionId
     * @return 详情
     */
    @Override
    public CustomerDTO getCustomerByUnionId(String unionId) {
        return customerService.getCustomerByUnionId(unionId);
    }


}
