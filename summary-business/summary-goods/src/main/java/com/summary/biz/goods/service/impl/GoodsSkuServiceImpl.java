package com.summary.biz.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.goods.entity.GoodsSkuDO;
import com.summary.biz.goods.mapper.GoodsSkuMapper;
import com.summary.biz.goods.service.GoodsImageService;
import com.summary.biz.goods.service.GoodsSkuService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.enums.ImageTypeEnum;
import com.summary.client.goods.enums.SaleStateEnum;
import com.summary.client.goods.param.CreateGoodsSkuParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.summary.common.core.constant.GlobalConstant.DefaultConstant.ZERO;

/**
 * <p>
 * 商品-SKU 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class GoodsSkuServiceImpl extends ServiceImpl<GoodsSkuMapper, GoodsSkuDO> implements GoodsSkuService {

    @Autowired
    private GoodsImageService goodsImageService;
    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public void saveGoodsSku(Long goodsId, List<CreateGoodsSkuParam> params) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        List<GoodsSkuDO> skus = new ArrayList<>();
        GoodsSkuDO sku = null;
        for (CreateGoodsSkuParam param : params) {
            sku = GoodsSkuDO.builder()
                    .skuId(IdWorker.getId())
                    .goodsId(goodsId)
                    .skuName(param.getSkuName())
                    .price(param.getPrice())
                    .image(param.getImage())
                    .stockNum(param.getStockNum())
                    .alertNum(param.getAlertNum())
                    .saleNum(ZERO)
                    .commentNum(ZERO)
                    .saleState(SaleStateEnum.sale.getCode())
                    .sort(param.getSort())
                    .build();

            // 保存sku图片
            goodsImageService.saveGoodsImage(null, sku.getSkuId(), ImageTypeEnum.sku, param.getImages());
            skus.add(sku);
        }

        saveBatch(skus);
    }

    @Override
    public List<GoodsSkuDO> getGoodsSkuByGoodsId(Long goodsId) {
        QueryWrapper<GoodsSkuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GoodsSkuDO::getGoodsId, goodsId);
        return goodsSkuMapper.selectList(queryWrapper);
    }

    @Override
    public List<CreateOrderCheckGoodsSkuDTO> getCreateOrderGoods(List<CreateOrderCheckParam> params) {
        return goodsSkuMapper.selectCreateOrderCheckGoodsSku(params);
    }

}
