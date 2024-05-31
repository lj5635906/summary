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
    private Integer marketable;

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
            Integer marketable
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.image = image;
        this.saleNum = saleNum;
        this.commentNum = commentNum;
        this.marketable = marketable;
    }


    @Override
    public Serializable pkVal() {
        return this.goodsId;
    }

}
