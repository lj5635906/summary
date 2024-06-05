package com.summary.front.web.seckill;

import com.summary.client.remote.SeckillGoodsRemoteService;
import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 秒杀商品 相关接口
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/web/seckillGoods")
public class SeckillGoodsController {

    @Autowired
    private SeckillGoodsRemoteService seckillGoodsRemoteService;

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    @PostMapping("/createSeckillGoods")
    public R<Long> createSeckillGoods(@Valid @RequestBody CreateSeckillGoodsParam param) {
        return seckillGoodsRemoteService.createSeckillGoods(param);
    }

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    @GetMapping("/getSeckillGoods")
    public R<SeckillGoodsDTO> getSeckillGoods(@RequestParam("seckillId") Long seckillId) {
        return seckillGoodsRemoteService.getSeckillGoods(seckillId);
    }

    /**
     * 获取秒杀商品列表
     *
     * @return 商品列表
     */
    @GetMapping("/getSeckillGoodsList")
    public R<List<SeckillGoodsDTO>> getSeckillGoodsList() {
        return seckillGoodsRemoteService.getSeckillGoodsList();
    }
}
