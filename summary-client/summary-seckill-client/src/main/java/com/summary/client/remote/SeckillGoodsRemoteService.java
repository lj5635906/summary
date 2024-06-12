package com.summary.client.remote;

import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 秒杀商品相关接口
 *
 * @author jie.luo
 * @since 2024/06/01
 */
public interface SeckillGoodsRemoteService {

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    Long createSeckillGoods(@Valid CreateSeckillGoodsParam param);

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    SeckillGoodsDTO getSeckillGoods(Long seckillId);

    /**
     * 获取秒杀商品列表
     *
     * @return 商品详情
     */
    List<SeckillGoodsDTO> getSeckillGoodsList();

}
