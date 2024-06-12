package com.summary.biz.seckill.provider;

import com.summary.biz.seckill.service.SeckillGoodsService;
import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.remote.SeckillGoodsRemoteService;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 秒杀商品 相关接口
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@DubboService
public class SeckillGoodsRemoteServiceProvider implements SeckillGoodsRemoteService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    @Override
    public Long createSeckillGoods(@Valid CreateSeckillGoodsParam param) {
        return seckillGoodsService.createSeckillGoods(param);
    }

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    @Override
    public SeckillGoodsDTO getSeckillGoods(Long seckillId) {
        return seckillGoodsService.getSeckillGoods(seckillId);
    }

    /**
     * 获取秒杀商品列表
     *
     * @return 商品列表
     */
    @Override
    public List<SeckillGoodsDTO> getSeckillGoodsList() {
        return seckillGoodsService.pageSeckillGoods();
    }
}
