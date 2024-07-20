package com.summary.client.remote;

import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.dto.GoodsSimpleDTO;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.goods.param.ChangeStockAndSaleParam;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品相关接口
 *
 * @author jie.luo
 * @since 2024/5/31
 */
@FeignClient(value = "summary-goods", path = "/goods")
public interface GoodsRemoteService {

    /**
     * 添加商品
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @PostMapping("/createGoods")
    Long createGoods(@Valid @RequestBody CreateGoodsParam param);

    /**
     * 商品-获取详情
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @GetMapping("/getGoodsByGoodsId")
    GoodsDTO getGoodsByGoodsId(@RequestParam(name = "goodsId") Long goodsId);

    /**
     * 商品-获取详情-简单版本
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @GetMapping("/getGoodsSimple")
    GoodsSimpleDTO getGoodsSimple(@RequestParam(name = "goodsId") Long goodsId);

    /**
     * 根据skuId获取商品sku
     *
     * @param skuId skuId
     * @return {@link GoodsSkuDTO}
     */
    @GetMapping("/getGoodsSkuBySkuId")
    GoodsSkuDTO getGoodsSkuBySkuId(@RequestParam(name = "skuId") Long skuId);

    /**
     * 获取创建订单时的商品信息
     *
     * @param params 下单check商品参数
     * @return 创建订单时的商品信息
     */
    @GetMapping("/getCreateOrderGoods")
    List<CreateOrderCheckGoodsSkuDTO> getCreateOrderGoods(@Valid @RequestBody List<CreateOrderCheckParam> params);

    /**
     * 商品下单扣库存与增加销量
     *
     * @param orderId orderId 订单id
     * @param params  下单check商品参数
     * @return 是否成功
     */
    @PostMapping("/changeStockAndSale")
    Boolean changeStockAndSale(@RequestParam(name = "orderId") Long orderId,
                               @Valid @RequestBody List<ChangeStockAndSaleParam> params);

    /**
     * 订单取消，恢复 库存与销量
     *
     * @param orderId orderId 订单id
     * @param param   商品参数
     * @return 是否成功
     */
    @PostMapping("/recoveryStockAndSale")
    Boolean recoveryStockAndSale(@RequestParam(name = "orderId") Long orderId, @Valid @RequestBody ChangeStockAndSaleParam param);

}
