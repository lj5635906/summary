package com.summary.client.seckill.param;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 秒杀参数
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Data
public class SeckillActionParam {

    /*** 秒杀id */
    private Long seckillId;

    /*** 秒杀购买数量 */
    @NotNull(message = "秒杀购买数量 不合法")
    private Integer num;

    private Long customerId;
}
