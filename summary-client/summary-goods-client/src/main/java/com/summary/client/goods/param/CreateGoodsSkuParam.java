package com.summary.client.goods.param;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * 添加商品-商品SKU
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateGoodsSkuParam {
    /*** SKU名称 */
    @NotBlank(message = "SKU名称不能为空")
    private String skuName;
    /*** 价格(单位/分) */
    @NotNull(message = "价格不能为空")
    @Range(message = "价格不合法(0-n)", min = 0)
    private Long price;
    /*** SKU图片-展示图 */
    private String image;
    /*** 商品SKU图片 */
    private List<String> images;
    /*** 库存数量 */
    @NotNull(message = "库存数量不能为空")
    @Range(message = "库存数量不合法(0-n)", min = 0)
    private Long stockNum;
    /*** 库存预警数量 */
    @NotNull(message = "库存数量不能为空")
    @Range(message = "库存数量不合法(0-n)", min = 0)
    private Long alertNum;
    /*** 排序 */
    private Integer sort;
}
