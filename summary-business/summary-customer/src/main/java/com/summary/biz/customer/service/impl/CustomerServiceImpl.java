package com.summary.biz.customer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.customer.entity.CustomerDO;
import com.summary.biz.customer.mapper.CustomerMapper;
import com.summary.biz.customer.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.customer.dto.CustomerDTO;
import com.summary.common.core.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, CustomerDO> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public CustomerDTO getCustomerByMobile(String mobile) {
        QueryWrapper<CustomerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerDO::getCustomerMobile, mobile);
        CustomerDO customerDO = customerMapper.selectOne(queryWrapper);
        return ConvertUtils.convert(customerDO, CustomerDTO.class);
    }

    @Override
    public CustomerDTO getCustomerByUnionId(String unionId) {
        QueryWrapper<CustomerDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CustomerDO::getUnionId, unionId);
        CustomerDO customerDO = customerMapper.selectOne(queryWrapper);
        return ConvertUtils.convert(customerDO, CustomerDTO.class);
    }
}
