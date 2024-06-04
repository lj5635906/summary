package com.summary.biz.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.biz.seckill.entity.SeckillGoodsDO;
import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;

import java.util.List;

/**
 * <p>
 * 秒杀商品 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface SeckillGoodsService extends IService<SeckillGoodsDO> {

    /**
     * 创建秒杀商品
     *
     * @param param {@link CreateSeckillGoodsParam}
     * @return 秒杀id
     */
    Long createSeckillGoods(CreateSeckillGoodsParam param);

    /**
     * 获取秒杀商品详情
     *
     * @param seckillId 秒杀id
     * @return 商品详情
     */
    SeckillGoodsDTO getSeckillGoods(Long seckillId);

    /**
     * 分页获取秒杀商品列表
     *
     * @return 商品列表
     */
    List<SeckillGoodsDTO> pageSeckillGoods();
}
