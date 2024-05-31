package com.summary.biz.goods.service;

import com.summary.biz.goods.entity.GoodsSkuDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.param.CreateGoodsSkuParam;
import com.summary.client.goods.param.CreateOrderCheckParam;

import java.util.List;

/**
 * <p>
 * 商品-SKU 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface GoodsSkuService extends IService<GoodsSkuDO> {

    /**
     * 保存商品sku
     *
     * @param goodsId 商品id
     * @param skus    sku集合
     */
    void saveGoodsSku(Long goodsId, List<CreateGoodsSkuParam> skus);

    /**
     * 根据goodsId获取商品skus
     *
     * @param goodsId 商品id
     * @return 商品skus
     */
    List<GoodsSkuDO> getGoodsSkuByGoodsId(Long goodsId);

    /**
     * 创建订单时查询的sku数据
     *
     * @param params 下单check商品参数
     * @return 商品skus
     */
    List<CreateOrderCheckGoodsSkuDTO> getCreateOrderGoods(List<CreateOrderCheckParam> params);
}
