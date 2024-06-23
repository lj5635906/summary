package com.summary.biz.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.summary.biz.search.config.es.ElasticsearchProperties;
import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.goods.dto.GoodsSkuSpecDTO;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author jie.luo
 * @since 2024/6/23
 */
public class Test {
    public static void main(String[] args) throws IOException {

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

        ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

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
//
        int pageNum = 1;
        int pageSize = 2;

        MatchQuery matchQuery = new MatchQuery();

        SearchResponse<GoodsSkuDTO> searchResponse = client.search(s -> s
                        .index("goods_sku")
                        .query(q -> q
                                .match(m -> m
                                        .field("goodsName")
                                        .query("华为（HUAWEI）")
                                        .operator(Operator.And)
                                        .field("brandName")
                                        .query("华为")

                                )
                        )
                        .size(pageSize)
                        .from(pageNum)
                ,
                GoodsSkuDTO.class
        );

        HitsMetadata<GoodsSkuDTO> hits = searchResponse.hits();
        hits.hits().forEach(hit -> System.out.println(hit));

        System.out.println(searchResponse);

//        DeleteResponse deleteResponse = client.delete(e -> e
//                .index("goods_sku")
//                .id("1803103625644343296")
//        );
//        System.out.println(deleteResponse);

    }

    public static GoodsSkuDTO buildGoodsSku1() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625644343296L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅典黑 12+512GB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(90);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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

        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku2() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625682092032L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅典黑 12+1TB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku3() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625732423680L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅川青 12+512GB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku4() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625770172416L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 雅川青 12+1TB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku5() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625812115456L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 白沙银 12+512GB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        return goodsSku;
    }

    public static GoodsSkuDTO buildGoodsSku7() {

        GoodsSkuDTO goodsSku = new GoodsSkuDTO();
        goodsSku.setSkuId(1803103625854058496L);
        goodsSku.setGoodsId(1803103625027780608L);
        goodsSku.setGoodsName("华为（HUAWEI）旗舰手机 Mate 60 Pro");
        goodsSku.setSkuName("华为（HUAWEI）旗舰手机 Mate 60 Pro 白沙银 12+1TB");
        goodsSku.setPrice(6999L);
        goodsSku.setStockNum(80);
        goodsSku.setAlertNum(10);
        goodsSku.setSaleNum(0);
        goodsSku.setCommentNum(0);
        goodsSku.setSaleNum(1);
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
        return goodsSku;
    }
}
