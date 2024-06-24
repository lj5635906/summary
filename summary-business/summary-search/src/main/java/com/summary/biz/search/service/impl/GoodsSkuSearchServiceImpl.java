package com.summary.biz.search.service.impl;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.json.JsonData;
import com.summary.biz.search.service.GoodsSkuSearchService;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.page.PageResult;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        elasticsearchClient.index(builder -> builder
                .index(INDEX_NAME)
                .id(String.valueOf(skuId))
                .document(goodsSku)
        );
    }

    @Override
    public PageResult<GoodsSkuDTO> searchGoodsSkus(GoodsSkuSearchParam param) throws IOException {

        PageResult<GoodsSkuDTO> pageResult = new PageResult<>();
        pageResult.setPageNum(param.getPageNum());
        pageResult.setPageSize(param.getPageSize());


        SearchResponse<GoodsSkuDTO> searchResponse = elasticsearchClient.search(s -> s
                        .index(INDEX_NAME)
                        .query(buildQuery(param))
                        .size(param.getPageSize())
                        .from((param.getPageNum() - 1) * param.getPageSize())
                        .highlight(buildHighlight())
                        .sort(buildSortOptions(param.getOrderBy()))
                ,
                GoodsSkuDTO.class
        );

        pageResult.setTotal(searchResponse.hits().total().value());
        pageResult.setTotalPages((int) ((pageResult.getTotal() / pageResult.getPageSize() == 0) ?
                (pageResult.getTotal() / pageResult.getPageSize()) :
                (pageResult.getTotal() / pageResult.getPageSize()) + 1)
        );

        pageResult.setContent(searchResponse.hits().hits().stream().map(hit -> {
            // 将非高亮数据替换高亮的数据
            if (null != hit.highlight()) {
                // 没有高亮的数据,需要获得高亮域的数据，替换 source 中没有高亮的数据
                hit.highlight().forEach((key, value) -> {
                    // 根据高亮字段，将非高亮数据替换高亮的数据
                    if ("skuName".equals(key)){
                        String highlightSkuName = value.get(0);
                        if (StrUtil.isNotBlank(highlightSkuName)) {
                            hit.source().setSkuName(highlightSkuName);
                        }
                    }
                });
            }
            return hit.source();
        }).toList());
        return pageResult;
    }

    /**
     * 构建高亮
     *
     * @return 。
     */
    private Highlight buildHighlight() {
        Highlight.Builder highlight = new Highlight.Builder();
        highlight.fields("skuName", h -> h.preTags("<font color='red'>").postTags("</font>"));
        return highlight.build();
    }

    /**
     * 构建查询条件
     *
     * @param param 。
     * @return 。
     */
    private Query buildQuery(GoodsSkuSearchParam param) {
        // 构建查询条件
        List<Query> conditions = new ArrayList<>();

        // 商品sku
        if (StrUtil.isNotBlank(param.getSkuName())) {
            conditions.add(new MatchQuery.Builder()
                    .field("skuName")
                    .query(param.getSkuName())
                    .build()._toQuery()
            );
        }

        // 品牌名称
        if (StrUtil.isNotBlank(param.getBrandName())) {
            conditions.add(MatchQuery.of(mq -> mq
                            .field("brandName")
                            .query(param.getBrandName()))
                    ._toQuery()
            );
        }

        // 价格范围
        RangeQuery.Builder priceRange = new RangeQuery.Builder();
        priceRange.field("price");
        if (null != param.getPriceMin() && param.getPriceMin() >= 0) {
            priceRange.gte(JsonData.of(param.getPriceMin()));
        } else {
            priceRange.gte(JsonData.of(0));
        }
        if (null != param.getPriceMax() && param.getPriceMax() >= 0) {
            priceRange.lte(JsonData.of(param.getPriceMax()));
        }
        conditions.add(priceRange.build()._toQuery());

        return new Query.Builder()
                .bool(b -> b.should(conditions))
                .build();
    }

    /**
     * 组装排序条件
     *
     * @param orderBy 排序: 0-默认排序, 1-价格升序, 2-价格降序, 3-销量降序, 4-评论数降序
     * @return .
     */
    private SortOptions buildSortOptions(Integer orderBy) {
        SortOptions.Builder sortOptions = new SortOptions.Builder();
        switch (orderBy) {
            case 1:
                sortOptions.field(f -> f.field("price").order(SortOrder.Asc));
                break;
            case 2:
                sortOptions.field(f -> f.field("price").order(SortOrder.Desc));
                break;
            case 3:
                sortOptions.field(f -> f.field("saleNum").order(SortOrder.Desc));
                break;
            case 4:
                sortOptions.field(f -> f.field("commentNum").order(SortOrder.Desc));
                break;
            default:
                sortOptions.field(f -> f.field("createTime").order(SortOrder.Desc));
                break;
        }
        return sortOptions.build();
    }
}
