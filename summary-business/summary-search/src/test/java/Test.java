import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery.Builder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.elasticsearch.core.search.HighlightBase;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.summary.biz.search.config.ElasticsearchProperties;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.goods.dto.GoodsSkuSpecDTO;
import com.summary.common.core.json.LocalDateOfEpochDayDeserializer;
import com.summary.common.core.json.LocalDateTimeOfEpochMilliDeserializer;
import com.summary.common.core.json.LocalDateTimeToEpochMilliSerializer;
import com.summary.common.core.json.LocalDateToEpochDaySerializer;
import com.summary.common.core.utils.DateTimeUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_PATTERN;

/**
 * @author jie.luo
 * @since 2024/6/23
 */
public class Test {

    public static ElasticsearchTransport elasticsearchTransport() {
        ElasticsearchProperties elasticsearchProperties = new ElasticsearchProperties();
        elasticsearchProperties.setHosts(Collections.singleton("http://192.168.31.101:9200"));
        elasticsearchProperties.setUsername("elastic");
        elasticsearchProperties.setPassword("m3I9YBQmmUGB2DTdsnOm");

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(elasticsearchProperties.getUsername(), elasticsearchProperties.getPassword()));

        HttpHost[] httpHosts = new HttpHost[elasticsearchProperties.getHosts().size()];
        int index = 0;
        for (String host : elasticsearchProperties.getHosts()) {
            httpHosts[index] = HttpHost.create(host);
            index++;
        }

        RestClient restClient = RestClient
                .builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    return httpClientBuilder;
                })
                .build();

        JacksonJsonpMapper jacksonJsonpMapper = new JacksonJsonpMapper();
        // 使用JSR310提供的序列化类,里面包含了大量的JDK8时间序列化类
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeToEpochMilliSerializer());
        timeModule.addSerializer(LocalDate.class, new LocalDateToEpochDaySerializer());
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeOfEpochMilliDeserializer());
        timeModule.addDeserializer(LocalDate.class, new LocalDateOfEpochDayDeserializer());
        jacksonJsonpMapper.objectMapper().registerModule(timeModule);

        return new RestClientTransport(restClient, jacksonJsonpMapper);
    }

    public static void main(String[] args) throws IOException {
        ElasticsearchTransport transport = elasticsearchTransport();
        ElasticsearchClient client = new ElasticsearchClient(transport);

//        List<GoodsSkuDTO> goodsSkus = List.of(buildGoodsSku1(), buildGoodsSku2(), buildGoodsSku3(), buildGoodsSku4(), buildGoodsSku5(), buildGoodsSku6());
//
//        for (GoodsSkuDTO goodsSku : goodsSkus) {
//            IndexResponse response = client.index(i -> i
//                    .index("goods_sku")
//                    .id(goodsSku.getSkuId().toString())
//                    .document(goodsSku)
//            );
//            System.out.println(response);
//        }

//        GetResponse<GoodsSkuDTO> getResponse = client.get(i -> i
//                .index("goods_sku")
//                .id("1803103625644343296"), GoodsSkuDTO.class
//        );
//        if (getResponse.found()) {
//            GoodsSkuDTO source = getResponse.source();
//            System.out.println(source);
//        } else {
//            System.out.println("未找到");
//        }


//        DeleteResponse deleteResponse = client.delete(e -> e
//                .index("goods_sku")
//                .id("1803103625644343296")
//        );
//        System.out.println(deleteResponse);

//        searchDemo(client);

        System.out.println();

        client.shutdown();
        transport.close();
    }

    public static void searchDemo(ElasticsearchClient client) throws IOException {
        int pageNum = 1;
        int pageSize = 2;

        String skuName = "华为（HUAWEI）";
        String brandName = "华为";
        Long priceMin = 6000L;
        Long priceMax = 7000L;
        Integer orderBy = 0;

        List<Query> conditions = new ArrayList<>();
        if (StrUtil.isNotBlank(skuName)) {
            conditions.add(new Builder()
                    .field("skuName")
                    .query("华为（HUAWEI）")
                    .build()._toQuery()
            );
        }

        if (StrUtil.isNotBlank(brandName)) {
            conditions.add(MatchQuery.of(mq -> mq
                            .field("brandName")
                            .query("华为"))
                    ._toQuery()
            );
        }

        // 价格范围
        RangeQuery.Builder priceRange = new RangeQuery.Builder();
        priceRange.field("price");
        if (null != priceMin && priceMin >= 0) {
            priceRange.gte(JsonData.of(priceMin));
        } else {
            priceRange.gte(JsonData.of(0));
        }
        if (null != priceMax && priceMax >= 0) {
            priceRange.lte(JsonData.of(priceMax));
        }
        conditions.add(priceRange.build()._toQuery());

        Highlight.Builder highlight = new Highlight.Builder();
        highlight.fields("skuName", h -> h.preTags("<font color='red'>").postTags("</font>"));

        Query query = new Query.Builder()
                .bool(b -> b.should(conditions))
                .build();

        System.out.println(query.toString());

        SearchResponse<GoodsSkuDTO> searchResponse = client.search(s -> s
                        .index("goods_sku")
                        .query(query)
                        .size(pageSize)
                        .highlight(highlight.build())
                        .from((pageNum - 1) * pageSize)
                        .sort(buildSortOptions(orderBy))
                ,
                GoodsSkuDTO.class
        );

        HitsMetadata<GoodsSkuDTO> hits = searchResponse.hits();
        System.out.println(searchResponse.hits().total().value());
        List<GoodsSkuDTO> list = searchResponse.hits().hits().stream().map(hit -> {
            if (null != hit.highlight()) {
                hit.highlight().forEach((key, value) -> {
                    if ("skuName".equals(key)){
                        String highlightSkuName = value.get(0);
                        if (StrUtil.isNotBlank(highlightSkuName)) {
                            hit.source().setSkuName(highlightSkuName);
                        }
                    }
                });
            }
            return hit.source();
        }).toList();
        System.out.println(list);
        System.out.println(searchResponse);
    }

    private static SortOptions buildSortOptions(Integer orderBy) {
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

    public static GoodsSkuDTO buildGoodsSku1() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625644343296L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅典黑 12+512GB");
        goodsSku.setPrice(1999L);
        goodsSku.setStockNum(90);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(1);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+512GB"),
                new GoodsSkuSpecDTO("颜色", "雅典黑")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-22 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-22 12:25:35", NORM_DATETIME_PATTERN));

        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku2() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625682092032L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅典黑 12+1TB");
        goodsSku.setPrice(2999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(2);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+1TB"),
                new GoodsSkuSpecDTO("颜色", "雅典黑")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-23 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-24 12:25:35", NORM_DATETIME_PATTERN));
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku3() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625732423680L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅川青 12+512GB");
        goodsSku.setPrice(3999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(3);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+512GB"),
                new GoodsSkuSpecDTO("颜色", "雅川青")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-24 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-25 12:25:35", NORM_DATETIME_PATTERN));
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku4() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625770172416L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅川青 12+1TB");
        goodsSku.setPrice(4999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(4);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+1TB"),
                new GoodsSkuSpecDTO("颜色", "雅川青")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-22 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-27 12:25:35", NORM_DATETIME_PATTERN));
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku5() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625812115456L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 白沙银 12+512GB");
        goodsSku.setPrice(5999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(5);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+512GB"),
                new GoodsSkuSpecDTO("颜色", "白沙银")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-28 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-05-29 12:25:35", NORM_DATETIME_PATTERN));
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku6() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625854058496L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 白沙银 12+1TB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(6);
        goodsSku.setCommentNum(0);
        goodsSku.setSpecs(List.of(
                new GoodsSkuSpecDTO("版本", "12+1TB"),
                new GoodsSkuSpecDTO("颜色", "白沙银")
        ));
        goodsSku.setSort(1);
        goodsSku.setImage("https://pic2.zhimg.com/v2-54b9998c71c9ec5f312acea4b2603901_r.jpg");
        goodsSku.setImages(List.of(
                "https://imgservice.suning.cn/uimg1/b2c/image/vv911Qns_4th5GNJ009dzQ.png_800w_800h_4e",
                "https://img1.baidu.com/it/u=3905784761,813784250&fm=253&fmt=auto&app=120&f=JPEG?w=667&h=500"
        ));
        goodsSku.setCategoryId(5L);
        goodsSku.setCategoryName("拍照手机");
        goodsSku.setBrandId(1L);
        goodsSku.setBrandName("华为");
        goodsSku.setCreateTime(DateTimeUtils.convertStringToLocalDateTime("2024-06-01 12:25:35", NORM_DATETIME_PATTERN));
        goodsSku.setUpdateTime(DateTimeUtils.convertStringToLocalDateTime("2024-06-02 12:25:35", NORM_DATETIME_PATTERN));
        return goodsSku;
    }


}
