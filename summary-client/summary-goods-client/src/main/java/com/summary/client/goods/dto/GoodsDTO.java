package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsDTO implements Serializable {
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品图片-展示图 */
    private String image;
    /*** 商品图片 */
    private List<String> images;
    /*** 商品介绍 */
    private String introduction;
    /*** 商品自定义参数 */
    private List<GoodsParamDTO> parameters;
    /*** 销售数量 */
    private Integer saleNum;
    /*** 评论数量 */
    private Integer commentNum;
    /*** 是否上架: 0-已上架,1-已下架 */
    private Boolean enableMarketable;
    /*** 是否启用规格: 0/false-未启用,1/true-启用 */
    private Boolean enableSpec;
    /*** 商品spu规格 */
    private List<GoodsSpecItemDTO> specItem;
    /*** 商品SKU */
    private List<GoodsSkuDTO> skus;
    /*** 一级分类 */
    private Long category1Id;
    /*** 二级分类 */
    private Long category2Id;
    /*** 三级分类 */
    private Long category3Id;
    /*** 品牌id */
    private Long brandId;
}
