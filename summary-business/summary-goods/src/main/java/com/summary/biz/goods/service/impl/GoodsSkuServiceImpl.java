package com.summary.biz.goods.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.goods.entity.BrandDO;
import com.summary.biz.goods.entity.CategoryDO;
import com.summary.biz.goods.entity.GoodsImageDO;
import com.summary.biz.goods.entity.GoodsSkuDO;
import com.summary.biz.goods.mapper.GoodsSkuMapper;
import com.summary.biz.goods.service.BrandService;
import com.summary.biz.goods.service.CategoryService;
import com.summary.biz.goods.service.GoodsImageService;
import com.summary.biz.goods.service.GoodsSkuService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.goods.dto.GoodsSkuSpecDTO;
import com.summary.client.goods.enums.ImageTypeEnum;
import com.summary.client.goods.enums.SaleStateEnum;
import com.summary.client.goods.param.ChangeStockAndSaleParam;
import com.summary.client.goods.param.CreateGoodsSkuParam;
import com.summary.client.goods.param.CreateGoodsSkuSpecParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.component.generator.id.snowflake.IdWorker;
import com.summary.component.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.summary.client.goods.code.GoodsExceptionCode.goods_stock_lack;
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
    @Autowired
    private DistributedLock distributedLock;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void saveGoodsSku(Long goodsId, String goodsName, List<CreateGoodsSkuParam> params) {
        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        List<GoodsSkuDO> skus = new ArrayList<>();
        GoodsSkuDO sku = null;
        for (CreateGoodsSkuParam param : params) {

            // 品牌
            BrandDO brand = brandService.getById(param.getBrandId());
            // 分类
            CategoryDO category = categoryService.getById(param.getCategoryId());

            sku = GoodsSkuDO.builder()
                    .skuId(IdWorker.nextId())
                    .goodsId(goodsId)
                    .goodsName(goodsName)
                    .goodsName(goodsName)
                    .skuName(param.getSkuName())
                    .price(param.getPrice())
                    .image(param.getImage())
                    .stockNum(param.getStockNum())
                    .alertNum(param.getAlertNum())
                    .saleNum(ZERO).commentNum(ZERO)
                    .saleState(SaleStateEnum.sale.getCode())
                    .sort(param.getSort())
                    .categoryId(param.getCategoryId())
                    .categoryName(null != category ? category.getCategoryName() : null)
                    .brandId(param.getBrandId())
                    .brandName(brand != null ? brand.getBrandName() : null)
                    .build();

            // 处理规格
            List<CreateGoodsSkuSpecParam> specs = param.getSpecs();
            if (!CollectionUtils.isEmpty(specs)) {
                JSONObject jsonObject = new JSONObject();
                for (CreateGoodsSkuSpecParam spec : specs) {
                    jsonObject.put(spec.getName(), spec.getOption());
                }
                sku.setSpec(jsonObject.toString());
            }

            // 保存sku图片
            goodsImageService.saveGoodsImage(null, sku.getSkuId(), ImageTypeEnum.sku, param.getImages());
            skus.add(sku);
        }

        saveBatch(skus);
    }

    @Override
    public List<GoodsSkuDO> getGoodsSkuByGoodsId(Long goodsId) {
        QueryWrapper<GoodsSkuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsSkuDO::getGoodsId, goodsId);
        return goodsSkuMapper.selectList(queryWrapper);
    }

    @Override
    public List<CreateOrderCheckGoodsSkuDTO> getCreateOrderGoods(List<CreateOrderCheckParam> params) {
        return goodsSkuMapper.selectCreateOrderCheckGoodsSku(params);
    }

    @Override
    public void changeStockAndSale(Long orderId, List<ChangeStockAndSaleParam> params) {

        // 通过分布式锁，锁定当前订单的扣库存操作，防止该订单重复扣库存
        String orderChangeStockLockName = "changeStock:order:" + orderId;
        distributedLock.acquire(orderChangeStockLockName);
        // 商品更改库存锁
        String goodsChangeStockLockName;
        try {
            for (ChangeStockAndSaleParam param : params) {
                goodsChangeStockLockName = "changeStock:sku:" + param.getSkuId();
                distributedLock.acquire(goodsChangeStockLockName);

                int count = goodsSkuMapper.changeStockAndSale(param.getSkuId(), param.getNum());
                if (count <= 0) {
                    distributedLock.release(goodsChangeStockLockName);
                    throw new CustomException(goods_stock_lack);
                }
                distributedLock.release(goodsChangeStockLockName);
            }
        } finally {
            distributedLock.release(orderChangeStockLockName);
        }
    }

    @Override
    public void recoveryStockAndSale(Long orderId, ChangeStockAndSaleParam param) {
        GoodsSkuDO sku = this.getById(param.getSkuId());
        GoodsSkuDO modify = GoodsSkuDO.builder()
                .skuId(param.getSkuId())
                .stockNum(sku.getStockNum() + param.getNum())
                .saleNum(sku.getSaleNum() - param.getNum())
                .build();
        goodsSkuMapper.updateById(modify);
    }

    @Override
    public GoodsSkuDTO getGoodsSkuBySkuId(Long skuId) {
        GoodsSkuDO goodsSkuDO = super.getById(skuId);
        if (null == goodsSkuDO) {
            return null;
        }

        GoodsSkuDTO sku = ConvertUtils.convert(goodsSkuDO, GoodsSkuDTO.class);
        List<GoodsImageDO> skuImages = goodsImageService.getGoodsImagesBySkuId(skuId);
        sku.setImages(skuImages == null ? null : skuImages.stream().map(GoodsImageDO::getImageUrl).collect(Collectors.toList()));

        // 处理商品sku规格
        String specJsonStr = goodsSkuDO.getSpec();
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

        return sku;
    }

}
