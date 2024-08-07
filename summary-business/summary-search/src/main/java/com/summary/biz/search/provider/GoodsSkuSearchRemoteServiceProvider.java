package com.summary.biz.search.provider;

import com.summary.biz.search.service.GoodsSkuSearchService;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsSkuSearchRemoteService;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 商品搜索相关接口
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@RestController
@RequestMapping("/search/goods")
public class GoodsSkuSearchRemoteServiceProvider implements GoodsSkuSearchRemoteService {

    @Autowired
    private GoodsSkuSearchService goodsSkuSearchService;

    @Override
    @RequestMapping("importGoodsSkuToElasticsearch")
    public Long importGoodsSkuToElasticsearch(@RequestParam(name = "skuId") Long skuId) throws IOException {
        return goodsSkuSearchService.importGoodsSkuToElasticsearch(skuId);
    }

    @Override
    @PostMapping("searchGoodsSkus")
    public PageResult<GoodsSkuDTO> searchGoodsSkus(@RequestBody GoodsSkuSearchParam param) throws IOException {
        return goodsSkuSearchService.searchGoodsSkus(param);
    }
}
