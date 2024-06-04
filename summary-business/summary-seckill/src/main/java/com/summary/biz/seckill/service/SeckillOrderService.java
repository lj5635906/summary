package com.summary.biz.seckill.service;

import com.summary.client.seckill.msg.CreateSeckillOrderMsg;

/**
 * 秒杀订单相关
 *
 * @author jie.luo
 * @since 2024/6/4
 */
public interface SeckillOrderService {
    /**
     * 创建秒杀订单
     *
     * @param msg .
     */
    void createSeckillOrder(CreateSeckillOrderMsg msg);

    /**
     * 秒杀订单超时取消
     *
     * @param customerId .
     * @param seckillId  .
     */
    void seckillOrderTimeoutCancel(Long customerId, Long seckillId);
}
