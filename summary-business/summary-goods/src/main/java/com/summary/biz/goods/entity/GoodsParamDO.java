package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 商品自定义参数
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("goods_param")
public class GoodsParamDO extends BaseDO<GoodsParamDO> {

    private static final long serialVersionUID = 1L;
    /*** 商品参数id */
    @TableId(value = "param_id", type = IdType.AUTO)
    private Long paramId;
    /*** 商品id */
    private Long goodsId;
    /*** 参数名 */
    private String paramName;
    /*** 参数值 */
    private String paramValue;

    @Builder
    public GoodsParamDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long paramId,
            Long goodsId,
            String paramName,
            String paramValue
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.paramId = paramId;
        this.goodsId = goodsId;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }


    @Override
    public Serializable pkVal() {
        return this.paramId;
    }

}
