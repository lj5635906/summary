package com.summary.client.goods.dto;

import lombok.Data;

import java.util.List;

/**
 * 商品spu规格
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class GoodsSpecItemDTO {
    /**
     * 商品spu规格名称
     */
    private String name;
    /**
     * 商品spu规格选项
     */
    private List<String> options;
}
