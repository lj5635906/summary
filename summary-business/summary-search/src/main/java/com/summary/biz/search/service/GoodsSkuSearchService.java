package com.summary.biz.search.service;

import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.page.PageResult;

import java.io.IOException;

/**
 * 搜索相关
 *
 * @author jie.luo
 * @since 2024/6/22
 */
public interface GoodsSkuSearchService {

    /**
     * 导入商品sku数据到 Elasticsearch
     *
     * @param skuId .
     * @return skuId
     * @throws IOException .
     */
    Long importGoodsSkuToElasticsearch(Long skuId) throws IOException;

    /**
     * 商品sku 搜索
     *
     * @param param .
     * @return .
     * @throws IOException .
     */
    PageResult<GoodsSkuDTO> searchGoodsSkus(GoodsSkuSearchParam param) throws IOException;
}
