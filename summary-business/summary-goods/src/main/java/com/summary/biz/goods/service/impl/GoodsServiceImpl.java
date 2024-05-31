package com.summary.biz.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.summary.biz.goods.entity.*;
import com.summary.biz.goods.mapper.GoodsMapper;
import com.summary.biz.goods.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.goods.dto.*;
import com.summary.client.goods.enums.ImageTypeEnum;
import com.summary.client.goods.enums.MarketableEnum;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.common.core.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.summary.common.core.constant.GlobalConstant.DefaultConstant.ZERO;

/**
 * <p>
 * 商品-SPU 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsDO> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsImageService goodsImageService;
    @Autowired
    private GoodsIntroductionService goodsIntroductionService;
    @Autowired
    private GoodsParamService goodsParamService;
    @Autowired
    private GoodsSkuService goodsSkuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createGoods(CreateGoodsParam param) {
        GoodsDO goods = GoodsDO.builder().goodsId(IdWorker.getId()).goodsName(param.getGoodsName()).marketable(MarketableEnum.down.getCode()).saleNum(ZERO).image(param.getImage()).build();

        // 保存商品
        goodsMapper.insert(goods);
        // 保存商品图片
        goodsImageService.saveGoodsImage(goods.getGoodsId(), null, ImageTypeEnum.spu, param.getImages());
        // 保存商品介绍
        goodsIntroductionService.saveGoodsIntroduction(goods.getGoodsId(), param.getIntroduction());
        // 保存商品自定义参数
        goodsParamService.saveGoodsParam(goods.getGoodsId(), param.getParameters());
        // 保存商品sku
        goodsSkuService.saveGoodsSku(goods.getGoodsId(), param.getSkus());

        return goods.getGoodsId();
    }

    @Override
    public GoodsDTO getGoodsByGoodsId(Long goodsId) {

        GoodsDO goodsDO = this.getById(goodsId);
        if (goodsDO == null) {
            return null;
        }
        GoodsDTO goods = ConvertUtils.convert(goodsDO, GoodsDTO.class);

        // 保存商品介绍
        GoodsIntroductionDO goodsIntroduction = goodsIntroductionService.getGoodsIntroductionByGoodsId(goodsId);
        goods.setIntroduction(null != goodsIntroduction ? goodsIntroduction.getIntroduction() : null);

        // 商品自定义参数
        List<GoodsParamDO> goodsParams = goodsParamService.getGoodsParamByGoodsId(goodsId);
        goods.setParameters(ConvertUtils.convertList(goodsParams, GoodsParamDTO.class));

        // 商品图片
        List<GoodsImageDO> goodsImageList = goodsImageService.getGoodsImagesByGoodsId(goodsId);
        if (!CollectionUtils.isEmpty(goodsImageList)) {
            List<String> collect = goodsImageList.stream().map(GoodsImageDO::getImageUrl).collect(Collectors.toList());
            goods.setImages(collect);
        }

        // 查询商品skus 并组装sku的图片
        List<GoodsSkuDO> skuList = goodsSkuService.getGoodsSkuByGoodsId(goodsId);
        if (!CollectionUtils.isEmpty(skuList)) {

            List<GoodsSkuDTO> skus = new ArrayList<>();
            // 所有sku的图片
            List<GoodsImageDO> skuImageList = goodsImageService.getGoodsImagesBySkuIds(skuList.stream().map(GoodsSkuDO::getSkuId).collect(Collectors.toList()));
            // 所有sku的图片 ==> Map<skuId,images>
            Map<Long, List<GoodsImageDO>> skuImageMap = null;
            if (!CollectionUtils.isEmpty(skuImageList)) {
                skuImageMap = skuImageList.stream().collect(Collectors.groupingBy(GoodsImageDO::getSkuId));
            }

            for (GoodsSkuDO skuDO : skuList) {
                GoodsSkuDTO sku = ConvertUtils.convert(goodsDO, GoodsSkuDTO.class);
                // 设置当前sku的图片
                if (CollectionUtils.isEmpty(skuImageMap)) {
                    List<GoodsImageDO> skuImages = skuImageMap.get(skuDO.getSkuId());
                    sku.setImages(skuImages == null ? null : skuImages.stream().map(GoodsImageDO::getImageUrl).collect(Collectors.toList()));

                }
                skus.add(sku);
            }
        }

        return goods;
    }

    @Override
    public GoodsSimpleDTO getGoodsSimple(Long goodsId) {
        GoodsDO goodsDO = this.getById(goodsId);
        if (goodsDO == null) {
            return null;
        }
        GoodsSimpleDTO goods = ConvertUtils.convert(goodsDO, GoodsSimpleDTO.class);

        // 查询商品skus
        List<GoodsSkuDO> skuList = goodsSkuService.getGoodsSkuByGoodsId(goodsId);
        List<GoodsSkuSimpleDTO> skus = ConvertUtils.convertList(skuList, GoodsSkuSimpleDTO.class);
        goods.setSkus(skus);

        return goods;
    }

}
