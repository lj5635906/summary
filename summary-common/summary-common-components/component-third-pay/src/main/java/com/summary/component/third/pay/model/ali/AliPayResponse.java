package com.summary.component.third.pay.model.ali;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付相应
 *
 * @author jie.luo
 * @since 2024/7/20
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AliPayResponse {

    private String body;

}
