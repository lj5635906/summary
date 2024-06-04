package com.summary.client.remote;

import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 秒杀商品相关接口
 *
 * @author jie.luo
 * @since 2024/06/01
 */
@FeignClient(name = "summary-seckill", path = "/seckillGoods")
public interface SeckillGoodsRemoteService {

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    @PostMapping("/createSeckillGoods")
    R<Long> createSeckillGoods(@Valid @RequestBody CreateSeckillGoodsParam param);

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    @GetMapping("/getSeckillGoods")
    R<SeckillGoodsDTO> getSeckillGoods(@RequestParam("seckillId") Long seckillId);

    /**
     * 获取秒杀商品列表
     *
     * @return 商品详情
     */
    @GetMapping("/getSeckillGoodsList")
    R<List<SeckillGoodsDTO>> getSeckillGoodsList();

}
