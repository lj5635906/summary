package com.summary.client.goods.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品spu规格
 *
 * @author jie.luo
 * @since 2024/6/1
 */
@Data
public class CreateGoodsSpecParam implements Serializable {
    /**
     * 商品spu规格名称
     */
    private String name;
    /**
     * 商品spu规格选项
     */
    private List<String> options;
}
