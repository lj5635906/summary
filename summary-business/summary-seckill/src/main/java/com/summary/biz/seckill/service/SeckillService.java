package com.summary.biz.seckill.service;

import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.common.core.dto.R;

/**
 * 秒杀相关业务
 *
 * @author jie.luo
 * @since 2024/6/4
 */
public interface SeckillService {
    /**
     * 创建秒杀订单
     *
     * @param customerId 用户id
     * @param seckillId  秒杀id
     * @param num        秒杀数量
     * @return 返回秒杀状态信息
     */
    R<SeckillStateDTO> seckillAction(Long customerId, Long seckillId, Integer num);

    /**
     * 查询用户对应的 秒杀状态信息
     *
     * @param customerId 用户id
     * @param seckillId  秒杀id
     * @return 返回秒杀状态信息
     */
    SeckillStateDTO querySeckillState(Long customerId, Long seckillId);

}
