package com.summary.client.order.param.pay;

import com.summary.common.core.enums.ClientTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 订单发起支付参数
 *
 * @author jie.luo
 * @since 2024/07/22
 */
@Data
public class OrderPayParam {
    /*** 订单id */
    @NotNull(message = "orderId can not be null")
    private Long orderId;
    /**
     * 客户端类型 @see {@link ClientTypeEnum}
     */
    @NotNull(message = "clientType can not be null")
    @Range(min = 0, max = 2, message = "clientType must be in [0,2]")
    private Integer clientType;
    /**
     * appId
     */
    @NotBlank(message = "appId can not be blank")
    private String appId;
}
