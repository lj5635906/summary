package com.summary.biz.search.service;

import com.summary.client.goods.dto.GoodsSkuDTO;
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
     */
    void importGoodsSkuToElasticsearch(Long skuId) throws IOException;

    PageResult<GoodsSkuDTO> searchGoodsSku(String keyword, Integer pageNum, Integer pageSize) throws IOException;
}
