package com.summary.biz.search.provider;

import com.summary.biz.search.service.GoodsSkuSearchService;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsSkuSearchRemoteService;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.page.PageResult;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 商品搜索相关接口
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@DubboService
public class GoodsSkuSearchRemoteServiceProvider implements GoodsSkuSearchRemoteService {

    @Autowired
    private GoodsSkuSearchService goodsSkuSearchService;

    @Override
    public Long importGoodsSkuToElasticsearch(Long skuId) throws IOException {
        return goodsSkuSearchService.importGoodsSkuToElasticsearch(skuId);
    }

    @Override
    public PageResult<GoodsSkuDTO> searchGoodsSkus(GoodsSkuSearchParam param) throws IOException {
        return goodsSkuSearchService.searchGoodsSkus(param);
    }
}
