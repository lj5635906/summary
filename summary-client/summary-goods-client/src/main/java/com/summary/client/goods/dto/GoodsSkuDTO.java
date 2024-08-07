package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品sku
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsSkuDTO implements Serializable {
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品skuId */
    private Long skuId;
    /*** SKU名称 */
    private String skuName;
    /*** 价格(单位/分) */
    private Long price;
    /*** SKU图片-展示图 */
    private String image;
    /*** SKU图片 */
    private List<String> images;
    /*** 库存数量 */
    private Integer stockNum;
    /*** 库存预警数量 */
    private Integer alertNum;
    /*** 销售数量 */
    private Integer saleNum;
    /*** 评论数量 */
    private Integer commentNum;
    /*** 销售状态: 0-销售中,-1-已售罄 */
    private Integer saleState;
    /*** 商品sku规格 */
    private List<GoodsSkuSpecDTO> specs;
    /*** 排序 */
    private Integer sort;
    /*** 分类id */
    private Long categoryId;
    /*** 分类名称 */
    private String categoryName;
    /*** 品牌id */
    private Long brandId;
    /*** 品牌名称 */
    private String brandName;
    /*** 创建时间*/
    private LocalDateTime createTime;
    /*** 更新时间*/
    private LocalDateTime updateTime;
}
