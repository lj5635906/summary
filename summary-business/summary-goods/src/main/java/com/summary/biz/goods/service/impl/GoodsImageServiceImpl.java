package com.summary.biz.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.goods.entity.GoodsImageDO;
import com.summary.biz.goods.mapper.GoodsImageMapper;
import com.summary.biz.goods.service.GoodsImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.goods.enums.ImageTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品图片 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class GoodsImageServiceImpl extends ServiceImpl<GoodsImageMapper, GoodsImageDO> implements GoodsImageService {

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Override
    public void saveGoodsImage(Long goodsId, Long skuId, ImageTypeEnum imgType, List<String> images) {

        if (CollectionUtils.isEmpty(images)) {
            return;
        }

        List<GoodsImageDO> saveImages = new ArrayList<>();
        GoodsImageDO image = null;
        for (String imageUrl : images) {
            image = new GoodsImageDO();
            image.setImageUrl(imageUrl);
            image.setImageType(imgType.getCode());
            if (imgType.equals(ImageTypeEnum.spu)) {
                image.setGoodsId(goodsId);
            } else {
                image.setSkuId(skuId);
            }
            saveImages.add(image);
        }

        this.saveBatch(saveImages);
    }

    @Override
    public List<GoodsImageDO> getGoodsImagesByGoodsId(Long goodsId) {
        if (goodsId == null) {
            return null;
        }
        QueryWrapper<GoodsImageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GoodsImageDO::getGoodsId, goodsId);
        return goodsImageMapper.selectList(queryWrapper);
    }

    @Override
    public List<GoodsImageDO> getGoodsImagesBySkuIds(List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return null;
        }
        QueryWrapper<GoodsImageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .in(GoodsImageDO::getSkuId, skuIds);
        return goodsImageMapper.selectList(queryWrapper);
    }

    @Override
    public List<GoodsImageDO> getGoodsImagesBySkuId(Long skuId) {
        QueryWrapper<GoodsImageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GoodsImageDO::getSkuId, skuId);
        return goodsImageMapper.selectList(queryWrapper);
    }
}
