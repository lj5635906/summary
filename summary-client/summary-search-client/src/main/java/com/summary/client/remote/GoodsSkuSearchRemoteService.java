package com.summary.client.remote;

import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.page.PageResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * 商品搜索相关接口
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@FeignClient(value = "summary-search", path = "/search/goods")
public interface GoodsSkuSearchRemoteService {
    /**
     * 导入商品sku数据到 Elasticsearch
     *
     * @param skuId .
     * @return skuId
     * @throws IOException .
     */
    @RequestMapping("importGoodsSkuToElasticsearch")
    Long importGoodsSkuToElasticsearch(@RequestParam(name = "skuId") Long skuId) throws IOException;

    /**
     * 商品sku 搜索
     *
     * @param param .
     * @return .
     * @throws IOException .
     */
    @PostMapping("searchGoodsSkus")
    PageResult<GoodsSkuDTO> searchGoodsSkus(@RequestBody GoodsSkuSearchParam param) throws IOException;
}
