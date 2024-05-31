package com.summary.biz.customer.rest;

import com.summary.biz.customer.entity.CustomerDO;
import com.summary.biz.customer.service.CustomerService;
import com.summary.client.customer.dto.CustomerDTO;
import com.summary.client.remote.CustomerRemoteService;
import com.summary.common.core.dto.R;
import com.summary.common.core.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口
 *
 * @author jie.luo
 * @since 2024-05-31
 */
@RestController
@RequestMapping("/customer")
public class CustomerController implements CustomerRemoteService {

    @Autowired
    private CustomerService customerService;

    /**
     * 用户-获取详情
     *
     * @param customerId 用户id
     * @return 详情
     */
    @Override
    @GetMapping("/getCustomerByCustomerId")
    public R<CustomerDTO> getCustomerByCustomerId(@RequestParam("customerId") Long customerId) {
        CustomerDO customerDO = customerService.getById(customerId);
        return R.success(ConvertUtils.convert(customerDO, CustomerDTO.class));
    }

    /**
     * 用户-获取详情
     *
     * @param mobile 电话
     * @return 详情
     */
    @Override
    @GetMapping("/getCustomerByMobile")
    public R<CustomerDTO> getCustomerByMobile(@RequestParam("mobile") String mobile) {
        return R.success(customerService.getCustomerByMobile(mobile));
    }

    /**
     * 用户-获取详情
     *
     * @param unionId unionId
     * @return 详情
     */
    @Override
    @GetMapping("/getCustomerByUnionId")
    public R<CustomerDTO> getCustomerByUnionId(@RequestParam("unionId") String unionId) {
        return R.success(customerService.getCustomerByUnionId(unionId));
    }


}
