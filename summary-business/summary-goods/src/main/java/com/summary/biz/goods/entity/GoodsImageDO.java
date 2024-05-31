package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;

import java.io.Serializable;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 商品图片
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("goods_image")
public class GoodsImageDO extends BaseDO<GoodsImageDO> {

    private static final long serialVersionUID = 1L;
    /*** 商品图片id */
    @TableId(value = "image_id", type = IdType.AUTO)
    private Long imageId;
    /*** 商品id */
    private Long goodsId;
    /*** 商品-skuId */
    private Long skuId;
    /*** 商品名称 */
    private String imageUrl;
    /*** 图片类型: 0-商品SPU、1-商品SKU */
    private Integer imageType;

    @Builder
    public GoodsImageDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long imageId,
            Long goodsId,
            Long skuId,
            String imageUrl,
            Integer imageType
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.imageId = imageId;
        this.goodsId = goodsId;
        this.skuId = skuId;
        this.imageUrl = imageUrl;
        this.imageType = imageType;
    }


    @Override
    public Serializable pkVal() {
        return this.imageId;
    }

}
