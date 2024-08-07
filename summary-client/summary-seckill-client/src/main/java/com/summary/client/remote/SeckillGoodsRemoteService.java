package com.summary.client.remote;

import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
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
@FeignClient(value = "summary-seckill", path = "/seckill/goods")
public interface SeckillGoodsRemoteService {

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    @PostMapping("/createSeckillGoods")
    Long createSeckillGoods(@Valid @RequestBody CreateSeckillGoodsParam param);

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    @GetMapping("/getSeckillGoods")
    SeckillGoodsDTO getSeckillGoods(@RequestParam(name = "seckillId") Long seckillId);

    /**
     * 获取秒杀商品列表
     *
     * @return 商品详情
     */
    @GetMapping("/getSeckillGoodsList")
    List<SeckillGoodsDTO> getSeckillGoodsList();

}
