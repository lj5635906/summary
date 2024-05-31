package com.summary.client.goods.param;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 添加商品
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateGoodsParam {

    /*** 商品名称 */
    @NotBlank(message = "商品名称不能为空")
    private String goodsName;
    /*** 商品图片-展示图 */
    private String image;
    /*** 商品图片 */
    private List<String> images;
    /*** 商品介绍 */
    private String introduction;
    /*** 商品自定义参数 */
    private List<CreateGoodsCustomParam> parameters;
    /*** 商品SKU */
    @Valid
    private List<CreateGoodsSkuParam> skus;
}
