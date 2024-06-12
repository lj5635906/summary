package com.summary.client.remote;

import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.param.SeckillActionParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;

/**
 * 秒杀相关接口
 *
 * @author jie.luo
 * @since 2024/06/01
 */
public interface SeckillRemoteService {

    /**
     * 开始秒杀
     *
     * @param param {@link SeckillActionParam}
     * @return 返回秒杀状态信息
     */
    R<SeckillStateDTO> seckillAction(@Valid SeckillActionParam param);

    /**
     * 查询用户对应的 秒杀状态信息
     *
     * @param seckillId  秒杀id
     * @param customerId 用户id
     * @return 秒杀状态信息
     */
    SeckillStateDTO querySeckillState(Long seckillId, Long customerId);
}
