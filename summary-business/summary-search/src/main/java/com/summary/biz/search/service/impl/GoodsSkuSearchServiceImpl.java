package com.summary.biz.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.summary.biz.search.service.GoodsSkuSearchService;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.page.PageResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 搜索相关
 *
 * @author jie.luo
 * @since 2024/6/22
 */
@Service
public class GoodsSkuSearchServiceImpl implements GoodsSkuSearchService {

    private static final String INDEX_NAME = "goods_sku";

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @DubboReference
    private GoodsRemoteService goodsRemoteService;

    @Override
    public void importGoodsSkuToElasticsearch(Long skuId) throws IOException {

        GoodsSkuDTO goodsSku = goodsRemoteService.getGoodsSkuBySkuId(skuId);
        if (null == goodsSku) {
            return;
        }

        elasticsearchClient.index(builder -> builder.index(INDEX_NAME).id(String.valueOf(skuId)).document(goodsSku));
    }

    @Override
    public PageResult<GoodsSkuDTO> searchGoodsSku(String keyword, Integer pageNum, Integer pageSize) throws IOException {
        SearchResponse<GoodsSkuDTO> goodsSku1 = elasticsearchClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(q -> q
                                .match(m -> m
                                        .field("goodsName")
                                        .query("华为（HUAWEI）旗舰手机 Mate 60 Pro")
                                )
                        )
                        .size(pageSize)
                        .from(pageNum)
                ,
                GoodsSkuDTO.class
        );
        return null;
    }
}
