package com.summary.client.search.param;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 商品sku 搜索相关参数
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@Data
public class GoodsSkuSearchParam {
    /*** 页码 */
    private Integer pageNum;
    /*** 页大小 */
    private Integer pageSize;
    /*** 品牌名称 */
    private String brandName;
    /*** SKU名称 */
    private String skuName;
    /*** 最小价格(单位/分) */
    private Long priceMin;
    /*** 最小价格(单位/分) */
    private Long priceMax;
    /*** 商品sku规格参数 */
    private List<SpecParam> specs;
    /*** 排序: 0-默认排序, 1-价格升序, 2-价格降序, 3-销量降序, 4-评论数降序 */
    private Integer orderBy;

    /**
     * 商品sku规格参数
     */
    @Data
    public static class SpecParam {
        /**
         * 商品sku规格名称
         */
        private String name;
        /**
         * 商品sku规格值
         */
        private String option;
    }
}
