package com.summary.biz.goods.service;

import com.summary.biz.goods.entity.GoodsImageDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.goods.enums.ImageTypeEnum;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface GoodsImageService extends IService<GoodsImageDO> {

    /**
     * 保存商品图片
     *
     * @param goodsId 商品id
     * @param skuId   商品skuId
     * @param imgType {@link ImageTypeEnum}
     * @param images  图片集合
     */
    void saveGoodsImage(Long goodsId, Long skuId, ImageTypeEnum imgType, List<String> images);

    /**
     * 查询商品的图片
     *
     * @param goodsId 商品id
     * @return 商品的图片
     */
    List<GoodsImageDO> getGoodsImagesByGoodsId(Long goodsId);

    /**
     * 通过 skuIds 查询图片
     *
     * @param skuIds .
     * @return 图片
     */
    List<GoodsImageDO> getGoodsImagesBySkuIds(List<Long> skuIds);

    /**
     * 通过 skuId 查询图片
     *
     * @param skuId .
     * @return 图片
     */
    List<GoodsImageDO> getGoodsImagesBySkuId(Long skuId);
}
