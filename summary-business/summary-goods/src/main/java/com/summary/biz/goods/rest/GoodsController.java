package com.summary.biz.goods.rest;

import com.summary.biz.goods.service.GoodsService;
import com.summary.biz.goods.service.GoodsSkuService;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.dto.GoodsSimpleDTO;
import com.summary.client.goods.param.ChangeStockAndSaleParam;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 商品-SPU 前端控制器
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@RestController
@RequestMapping("/goods")
public class GoodsController implements GoodsRemoteService {

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
    public R<Long> createGoods(@Valid @RequestBody CreateGoodsParam param) {
        return R.success(goodsService.createGoods(param));
    }

    /**
     * 商品-获取详情
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @Override
    @GetMapping("/getGoodsByGoodsId")
    public R<GoodsDTO> getGoodsByGoodsId(@RequestParam("goodsId") Long goodsId) {
        return R.success(goodsService.getGoodsByGoodsId(goodsId));
    }

    /**
     * 商品-获取详情-简单版本
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @Override
    @GetMapping("/getGoodsSimple")
    public R<GoodsSimpleDTO> getGoodsSimple(@RequestParam("goodsId") Long goodsId) {
        return R.success(goodsService.getGoodsSimple(goodsId));
    }

    /**
     * 获取创建订单时的商品信息
     *
     * @param params 下单check商品参数
     * @return 创建订单时的商品信息
     */
    @PostMapping("/getCreateOrderGoods")
    public R<List<CreateOrderCheckGoodsSkuDTO>> getCreateOrderGoods(@Valid @RequestBody List<CreateOrderCheckParam> params) {
        return R.success(goodsSkuService.getCreateOrderGoods(params));
    }

    /**
     * 商品下单扣库存与增加销量
     *
     * @param params 下单商品sku
     * @return 是否成功
     */
    @Override
    @PostMapping("/changeStockAndSale")
    public R<Boolean> changeStockAndSale(@RequestParam("orderId") Long orderId, @Valid @RequestBody List<ChangeStockAndSaleParam> params) {
        goodsSkuService.changeStockAndSale(orderId, params);
        return R.success(true);
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
    public R<Boolean> recoveryStockAndSale(Long orderId, ChangeStockAndSaleParam param) {
        goodsSkuService.recoveryStockAndSale(orderId, param);
        return R.success(true);
    }
}
