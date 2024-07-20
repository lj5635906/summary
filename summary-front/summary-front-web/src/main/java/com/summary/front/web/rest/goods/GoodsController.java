package com.summary.front.web.rest.goods;

import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 商品相关接口
 *
 * @author jie.luo
 * @since 2024/6/12
 */
@RestController
@RequestMapping("/web/goods")
public class GoodsController {

    @Autowired
    private GoodsRemoteService goodsRemoteService;

    /**
     * 添加商品
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @PostMapping("/createGoods")
    public R<Long> createGoods(@Valid @RequestBody CreateGoodsParam param) {
        return R.success(goodsRemoteService.createGoods(param));
    }

    /**
     * 商品-获取详情
     *
     * @param goodsId 商品id
     * @return 详情
     */
    @GetMapping("/getGoodsByGoodsId")
    public R<GoodsDTO> getGoodsByGoodsId(@RequestParam(name = "goodsId") Long goodsId) {
        return R.success(goodsRemoteService.getGoodsByGoodsId(goodsId));
    }
}
