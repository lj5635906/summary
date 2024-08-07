package com.summary.biz.goods.provider;

import com.summary.biz.goods.service.GoodsService;
import com.summary.biz.goods.service.GoodsSkuService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.dto.GoodsSimpleDTO;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.goods.param.ChangeStockAndSaleParam;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.client.remote.GoodsRemoteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品-相关接口
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/goods")
public class GoodsRemoteServiceProvider implements GoodsRemoteService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsSkuService goodsSkuService;

    /**
     * 添加商品
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @Override
    @PostMapping("/createGoods")
    public Long createGoods(@Valid @RequestBody CreateGoodsParam param) {
        return goodsService.createGoods(param);
    }

    /**
     * 商品-获取详情
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @Override
    @GetMapping("/getGoodsByGoodsId")
    public GoodsDTO getGoodsByGoodsId(@RequestParam(name = "goodsId") Long goodsId) {
        return goodsService.getGoodsByGoodsId(goodsId);
    }

    /**
     * 商品-获取详情-简单版本
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @Override
    @GetMapping("/getGoodsSimple")
    public GoodsSimpleDTO getGoodsSimple(@RequestParam(name = "goodsId") Long goodsId) {
        return goodsService.getGoodsSimple(goodsId);
    }

    /**
     * 根据skuId获取商品sku
     *
     * @param skuId skuId
     * @return {@link GoodsSkuDTO}
     */
    @Override
    @GetMapping("/getGoodsSkuBySkuId")
    public GoodsSkuDTO getGoodsSkuBySkuId(@RequestParam(name = "skuId") Long skuId) {
        return goodsSkuService.getGoodsSkuBySkuId(skuId);
    }

    /**
     * 获取创建订单时的商品信息
     *
     * @param params 下单check商品参数
     * @return 创建订单时的商品信息
     */
    @Override
    @GetMapping("/getCreateOrderGoods")
    public List<CreateOrderCheckGoodsSkuDTO> getCreateOrderGoods(@Valid @RequestBody List<CreateOrderCheckParam> params) {
        return goodsSkuService.getCreateOrderGoods(params);
    }

    /**
     * 商品下单扣库存与增加销量
     *
     * @param params 下单商品sku
     * @return 是否成功
     */
    @Override
    @PostMapping("/changeStockAndSale")
    public Boolean changeStockAndSale(@RequestParam(name = "orderId") Long orderId,
                                      @Valid @RequestBody List<ChangeStockAndSaleParam> params) {
        goodsSkuService.changeStockAndSale(orderId, params);
        return true;
    }

    /**
     * 订单取消，恢复 库存与销量
     *
     * @param orderId orderId 订单id
     * @param param   商品参数
     * @return 是否成功
     */
    @Override
    @PostMapping("/recoveryStockAndSale")
    public Boolean recoveryStockAndSale(@RequestParam(name = "orderId") Long orderId, @Valid @RequestBody ChangeStockAndSaleParam param) {
        goodsSkuService.recoveryStockAndSale(orderId, param);
        return true;
    }
}
