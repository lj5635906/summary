package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsParamDTO implements Serializable {
    /*** 商品参数id */
    private Long paramId;
    /*** 参数名 */
    private String paramName;
    /*** 参数值 */
    private String paramValue;
}
