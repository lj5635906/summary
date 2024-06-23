package com.summary.biz.goods.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.goods.entity.*;
import com.summary.biz.goods.mapper.GoodsMapper;
import com.summary.biz.goods.service.*;
import com.summary.client.goods.dto.*;
import com.summary.client.goods.enums.ImageTypeEnum;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateGoodsSpecParam;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.component.generator.id.snowflake.IdWorker;
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

        GoodsDO goods = buildGoodsDO(param);

        // 保存商品
        goodsMapper.insert(goods);
        // 保存商品图片
        goodsImageService.saveGoodsImage(goods.getGoodsId(), null, ImageTypeEnum.spu, param.getImages());
        // 保存商品介绍
        goodsIntroductionService.saveGoodsIntroduction(goods.getGoodsId(), param.getIntroduction());
        // 保存商品自定义参数
        goodsParamService.saveGoodsParam(goods.getGoodsId(), param.getParameters());
        // 保存商品sku
        goodsSkuService.saveGoodsSku(goods.getGoodsId(), goods.getGoodsName(), param.getSkus());

        return goods.getGoodsId();
    }

    private GoodsDO buildGoodsDO(CreateGoodsParam param) {
        GoodsDO goods = GoodsDO.builder()
                .goodsId(IdWorker.nextId())
                .goodsName(param.getGoodsName())
                .enableMarketable(false)
                .saleNum(ZERO)
                .image(param.getImage())
                .enableSpec(param.getEnableSpec())
                .category1Id(param.getCategory1Id())
                .category2Id(param.getCategory2Id())
                .category3Id(param.getCategory3Id())
                .brandId(param.getBrandId())
                .build();

        if (!param.getEnableSpec()) {
            // 未启用规格
            return goods;
        }

        // 启用规格
        List<CreateGoodsSpecParam> specItem = param.getSpecItem();
        JSONObject jsonObject = new JSONObject();
        for (CreateGoodsSpecParam spec : specItem) {
            jsonObject.put(spec.getName(), spec.getOptions());
        }
        goods.setSpecItem(jsonObject.toString());

        return goods;
    }

    @Override
    public GoodsDTO getGoodsByGoodsId(Long goodsId) {

        GoodsDO goodsDO = this.getById(goodsId);
        if (goodsDO == null) {
            return null;
        }

        GoodsDTO goods = ConvertUtils.convert(goodsDO, GoodsDTO.class);

        // 处理商品spu规格集合
        String specItemJsonStr = goodsDO.getSpecItem();
        if (StrUtil.isNotBlank(specItemJsonStr)) {
            JSONObject jsonObject = new JSONObject(specItemJsonStr);
            List<GoodsSpecItemDTO> specItem = new ArrayList<>();
            GoodsSpecItemDTO item = null;
            for (Map.Entry<String, Object> json : jsonObject) {
                item = new GoodsSpecItemDTO();
                item.setName(json.getKey());
                item.setOptions(jsonObject.getJSONArray(json.getKey()).toList(String.class));
                specItem.add(item);
            }
            goods.setSpecItem(specItem);
        }

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
        if (CollectionUtils.isEmpty(skuList)) {
            return goods;
        }

        List<GoodsSkuDTO> skus = new ArrayList<>();
        // 所有sku的图片
        List<GoodsImageDO> skuImageList = goodsImageService.getGoodsImagesBySkuIds(skuList.stream().map(GoodsSkuDO::getSkuId).collect(Collectors.toList()));
        // 所有sku的图片 ==> Map<skuId,images>
        Map<Long, List<GoodsImageDO>> skuImageMap = null;
        if (!CollectionUtils.isEmpty(skuImageList)) {
            skuImageMap = skuImageList.stream().collect(Collectors.groupingBy(GoodsImageDO::getSkuId));
        }

        for (GoodsSkuDO skuDO : skuList) {
            GoodsSkuDTO sku = ConvertUtils.convert(skuDO, GoodsSkuDTO.class);
            // 设置当前sku的图片
            if (CollectionUtils.isEmpty(skuImageMap)) {
                continue;
            }
            List<GoodsImageDO> skuImages = skuImageMap.get(skuDO.getSkuId());
            sku.setImages(skuImages == null ? null : skuImages.stream().map(GoodsImageDO::getImageUrl).collect(Collectors.toList()));

            // 处理商品sku规格
            String specJsonStr = skuDO.getSpec();
            if (StrUtil.isNotBlank(specJsonStr)) {
                JSONObject jsonObject = new JSONObject(specJsonStr);
                List<GoodsSkuSpecDTO> specs = new ArrayList<>();
                GoodsSkuSpecDTO spec = null;
                for (Map.Entry<String, Object> json : jsonObject) {
                    spec = new GoodsSkuSpecDTO();
                    spec.setName(json.getKey());
                    spec.setOption(jsonObject.getStr(json.getKey()));
                    specs.add(spec);
                }
                sku.setSpecs(specs);
            }

            skus.add(sku);
        }

        goods.setSkus(skus);

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
