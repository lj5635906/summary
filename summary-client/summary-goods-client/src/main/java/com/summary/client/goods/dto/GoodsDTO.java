package com.summary.client.goods.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品详情
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsDTO {
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
    private Integer marketable;
    /*** 商品SKU */
    private List<GoodsSkuDTO> skus;
}
