package com.summary.front.web.rest.search;

import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsSkuSearchRemoteService;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 搜索相关接口
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@RestController
@RequestMapping("/web/search")
public class SearchController {

    @Autowired
    private GoodsSkuSearchRemoteService goodsSkuSearchRemoteService;

    /**
     * 导入商品sku数据到 Elasticsearch
     *
     * @param skuId .
     */
    @GetMapping("/importGoodsSkuToElasticsearch")
    public R<Long> importGoodsSkuToElasticsearch(@RequestParam(name = "skuId") Long skuId) throws IOException {
        return R.success(goodsSkuSearchRemoteService.importGoodsSkuToElasticsearch(skuId));
    }

    /**
     * 商品sku搜索
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @PostMapping("/searchGoodsSkus")
    public R<PageResult<GoodsSkuDTO>> searchGoodsSkus(@RequestParam @Valid GoodsSkuSearchParam param) throws IOException {
        return R.success(goodsSkuSearchRemoteService.searchGoodsSkus(param));
    }
}
