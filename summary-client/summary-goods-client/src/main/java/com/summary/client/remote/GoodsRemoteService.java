package com.summary.client.remote;

import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.dto.GoodsSimpleDTO;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.common.core.dto.R;
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
@FeignClient(name = "summary-goods", path = "/goods")
public interface GoodsRemoteService {

    /**
     * 添加商品
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @PostMapping("/createGoods")
    R<Long> createGoods(@Valid @RequestBody CreateGoodsParam param);

    /**
     * 商品-获取详情
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @GetMapping("/getGoodsByGoodsId")
    R<GoodsDTO> getGoodsByGoodsId(@RequestParam("goodsId") Long goodsId);

    /**
     * 商品-获取详情-简单版本
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @GetMapping("/getGoodsSimple")
    R<GoodsSimpleDTO> getGoodsSimple(@RequestParam("goodsId") Long goodsId);

    /**
     * 获取创建订单时的商品信息
     *
     * @param params 下单check商品参数
     * @return 商品详情简化版
     */
    @GetMapping("/getCreateOrderGoods")
    R<List<CreateOrderCheckGoodsSkuDTO>> getCreateOrderGoods(@Valid @RequestBody List<CreateOrderCheckParam> params);

}
