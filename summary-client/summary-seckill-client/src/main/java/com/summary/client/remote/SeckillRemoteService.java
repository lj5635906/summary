package com.summary.client.remote;

import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.param.SeckillActionParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 秒杀相关接口
 *
 * @author jie.luo
 * @since 2024/06/01
 */
@FeignClient(name = "summary-seckill", path = "/seckill")
public interface SeckillRemoteService {

    /**
     * 开始秒杀
     *
     * @param param {@link SeckillActionParam}
     * @return 返回秒杀状态信息
     */
    @PostMapping("/action")
    R<SeckillStateDTO> seckillAction(@Valid @RequestBody SeckillActionParam param);

    /**
     * 查询用户对应的 秒杀状态信息
     *
     * @param seckillId  秒杀id
     * @param customerId 用户id
     * @return 秒杀状态信息
     */
    @GetMapping("/querySeckillState")
    R<SeckillStateDTO> querySeckillState(@RequestParam("seckillId") Long seckillId, @RequestParam("customerId") Long customerId);
}
