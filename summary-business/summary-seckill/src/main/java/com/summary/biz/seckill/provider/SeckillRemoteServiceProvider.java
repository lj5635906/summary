package com.summary.biz.seckill.provider;

import com.summary.biz.seckill.service.SeckillService;
import com.summary.client.remote.SeckillRemoteService;
import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.param.SeckillActionParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 秒杀 相关接口
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@DubboService
public class SeckillRemoteServiceProvider implements SeckillRemoteService {

    @Autowired
    private SeckillService seckillService;

    /**
     * 开始秒杀
     *
     * @param param {@link SeckillActionParam}
     * @return 返回秒杀状态信息
     */
    @Override
    public R<SeckillStateDTO> seckillAction(@Valid SeckillActionParam param) {
        Long customerId = 1L;
        return seckillService.seckillAction(param.getCustomerId(), param.getSeckillId(), param.getNum());
    }

    /**
     * 查询用户对应的 秒杀状态信息
     *
     * @param seckillId  秒杀id
     * @param customerId 用户id
     * @return 秒杀状态信息
     */
    @Override
    public SeckillStateDTO querySeckillState(Long seckillId, Long customerId) {
        return seckillService.querySeckillState(customerId, seckillId);
    }

}
