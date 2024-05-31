package com.summary.biz.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.biz.goods.entity.GoodsIntroductionDO;

/**
 * <p>
 * 商品介绍 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface GoodsIntroductionService extends IService<GoodsIntroductionDO> {

    /**
     * 保存商品介绍
     *
     * @param goodsId      商品id
     * @param introduction 商品介绍
     */
    void saveGoodsIntroduction(Long goodsId, String introduction);

    /**
     * 获取商品介绍
     *
     * @param goodsId 商品id
     * @return 商品介绍
     */
    GoodsIntroductionDO getGoodsIntroductionByGoodsId(Long goodsId);
}
