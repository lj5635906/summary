package com.summary.client.remote;

import com.summary.client.customer.dto.CustomerDTO;
import com.summary.common.core.dto.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户相关接口
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@FeignClient(name = "summary-customer", path = "/customer")
public interface CustomerRemoteService {

    /**
     * 用户-获取详情
     *
     * @param customerId 用户id
     * @return 详情
     */
    @GetMapping("/getCustomerByCustomerId")
    R<CustomerDTO> getCustomerByCustomerId(@RequestParam("customerId") Long customerId);

    /**
     * 用户-获取详情
     *
     * @param mobile 电话
     * @return 详情
     */
    @GetMapping("/getCustomerByMobile")
    R<CustomerDTO> getCustomerByMobile(@RequestParam("mobile") String mobile);

    /**
     * 用户-获取详情
     *
     * @param unionId unionId
     * @return 详情
     */
    @GetMapping("/getCustomerByUnionId")
    R<CustomerDTO> getCustomerByUnionId(@RequestParam("unionId") String unionId);

}
