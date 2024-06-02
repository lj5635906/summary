package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 商品-SKU
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("goods_sku")
public class GoodsSkuDO extends BaseDO<GoodsSkuDO> {

    private static final long serialVersionUID = 1L;
    /*** 商品skuId */
    @TableId(value = "sku_id", type = IdType.AUTO)
    private Long skuId;
    /*** 商品id */
    private Long goodsId;
    /*** SKU名称 */
    private String skuName;
    /*** 价格(单位/分) */
    private Long price;
    /*** SKU图片-展示图 */
    private String image;
    /*** 库存数量 */
    private Long stockNum;
    /*** 库存预警数量 */
    private Long alertNum;
    /*** 销售数量 */
    private Integer saleNum;
    /*** 评论数量 */
    private Integer commentNum;
    /*** 销售状态: 0-销售中,-1-已售罄 */
    private Integer saleState;
    /*** 规格 */
    private String spec;
    /*** 排序 */
    private Integer sort;

    @Builder
    public GoodsSkuDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long skuId,
            Long goodsId,
            String skuName,
            Long price,
            String image,
            Long stockNum,
            Long alertNum,
            Integer saleNum,
            Integer commentNum,
            Integer saleState,
            String spec,
            Integer sort
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.skuId = skuId;
        this.goodsId = goodsId;
        this.skuName = skuName;
        this.price = price;
        this.image = image;
        this.stockNum = stockNum;
        this.alertNum = alertNum;
        this.saleNum = saleNum;
        this.commentNum = commentNum;
        this.saleState = saleState;
        this.spec = spec;
        this.sort = sort;
    }


    @Override
    public Serializable pkVal() {
        return this.skuId;
    }

}
