package com.summary.biz.customer.service;

import com.summary.biz.customer.entity.CustomerDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.customer.dto.CustomerDTO;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
public interface CustomerService extends IService<CustomerDO> {

    /**
     * 获取用户详情-电话
     *
     * @param mobile 电话
     * @return {@link CustomerDTO}
     */
    CustomerDTO getCustomerByMobile(String mobile);

    /**
     * 获取用户详情-unionId
     *
     * @param unionId unionId
     * @return {@link CustomerDTO}
     */
    CustomerDTO getCustomerByUnionId(String unionId);
}
