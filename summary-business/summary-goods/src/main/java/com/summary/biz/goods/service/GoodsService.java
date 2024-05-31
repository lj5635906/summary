package com.summary.biz.goods.service;

import com.summary.biz.goods.entity.GoodsDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.dto.GoodsSimpleDTO;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;

import java.util.List;

/**
 * <p>
 * 商品-SPU 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface GoodsService extends IService<GoodsDO> {

    /**
     * 添加商品
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    Long createGoods(CreateGoodsParam param);

    /**
     * 获取商品详情
     *
     * @param goodsId 商品id
     * @return {@link GoodsDO}
     */
    GoodsDTO getGoodsByGoodsId(Long goodsId);

    /**
     * 获取商品详情简化版
     *
     * @param goodsId 商品id
     * @return 商品详情简化版
     */
    GoodsSimpleDTO getGoodsSimple(Long goodsId);

}
