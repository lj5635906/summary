package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 商品介绍
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("goods_introduction")
public class GoodsIntroductionDO extends BaseDO<GoodsIntroductionDO> {

    private static final long serialVersionUID = 1L;
    /*** 商品介绍id */
    @TableId(value = "introduction_id", type = IdType.AUTO)
    private Long introductionId;
    /*** 商品id */
    private Long goodsId;
    /*** 商品介绍 */
    private String introduction;

    @Builder
    public GoodsIntroductionDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long introductionId,
            Long goodsId,
            String introduction
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.introductionId = introductionId;
        this.goodsId = goodsId;
        this.introduction = introduction;
    }


    @Override
    public Serializable pkVal() {
        return this.introductionId;
    }

}
