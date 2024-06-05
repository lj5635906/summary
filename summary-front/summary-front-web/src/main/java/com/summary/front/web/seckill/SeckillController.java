package com.summary.front.web.seckill;

import com.summary.client.remote.SeckillRemoteService;
import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.param.SeckillActionParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 秒杀相关接口
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@RestController
@RequestMapping("/web/seckill")
public class SeckillController {

    @Autowired
    private SeckillRemoteService seckillRemoteService;

    /**
     * 开始秒杀
     *
     * @param param {@link SeckillActionParam}
     * @return 返回秒杀状态信息
     */
    @PostMapping("/action")
    public R<SeckillStateDTO> seckillAction(@Valid @RequestBody SeckillActionParam param) {
        return seckillRemoteService.seckillAction(param);
    }

    /**
     * 查询用户对应的 秒杀状态信息
     *
     * @param seckillId  秒杀id
     * @param customerId 用户id
     * @return 秒杀状态信息
     */
    @GetMapping("/querySeckillState")
    public R<SeckillStateDTO> querySeckillState(@RequestParam("seckillId") Long seckillId, @RequestParam("customerId") Long customerId) {
        return seckillRemoteService.querySeckillState(customerId, seckillId);
    }
}
