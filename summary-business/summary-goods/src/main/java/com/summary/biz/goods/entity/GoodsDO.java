package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;

import java.io.Serializable;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 商品-SPU
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("goods")
public class GoodsDO extends BaseDO<GoodsDO> {

    private static final long serialVersionUID = 1L;
    /*** 商品id */
    @TableId(value = "goods_id", type = IdType.AUTO)
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品图片-展示图 */
    private String image;
    /*** 销售数量 */
    private Integer saleNum;
    /*** 评论数量 */
    private Integer commentNum;
    /*** 是否上架: 0-已上架,1-已下架 */
    private Boolean enableMarketable;
    /*** 是否启用规格: 0/false-未启用,1/true-启用 */
    private Boolean enableSpec;
    /*** 规格列表 */
    private String specItem;
    /*** 一级分类 */
    private Long category1Id;
    /*** 二级分类 */
    private Long category2Id;
    /*** 三级分类 */
    private Long category3Id;
    /*** 品牌id */
    private Long brandId;

    @Builder
    public GoodsDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long goodsId,
            String goodsName,
            String image,
            Integer saleNum,
            Integer commentNum,
            Boolean enableMarketable,
            Boolean enableSpec,
            String specItem,
            Long category1Id,
            Long category2Id,
            Long category3Id,
            Long brandId
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.image = image;
        this.saleNum = saleNum;
        this.commentNum = commentNum;
        this.enableMarketable = enableMarketable;
        this.enableSpec = enableSpec;
        this.specItem = specItem;
        this.category1Id = category1Id;
        this.category2Id = category2Id;
        this.category3Id = category3Id;
        this.brandId = brandId;
    }


    @Override
    public Serializable pkVal() {
        return this.goodsId;
    }

}
