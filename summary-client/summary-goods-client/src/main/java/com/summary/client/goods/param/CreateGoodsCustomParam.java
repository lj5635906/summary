package com.summary.client.goods.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 添加商品-商品自定义参数
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateGoodsCustomParam implements Serializable {
    /*** 参数名 */
    private String paramName;
    /*** 参数值 */
    private String paramValue;
}
