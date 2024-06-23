package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 品牌
 *
 * @author myabtis-plus
 * @since 2024-06-23
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("brand")
public class BrandDO extends BaseDO<BrandDO> {

    private static final long serialVersionUID = 1L;
    /*** 品牌id */
    @TableId(value = "brand_id", type = IdType.AUTO)
    private Long brandId;
    /*** 品牌名称 */
    private String brandName;
    /*** 品牌的首字母 */
    private String brandLetter;
    /*** 品牌logo */
    private String brandLogo;
    /*** 排序 */
    private Integer sort;

    @Builder
    public BrandDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long brandId,
            String brandName,
            String brandLetter,
            String brandLogo,
            Integer sort
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandLetter = brandLetter;
        this.brandLogo = brandLogo;
        this.sort = sort;
    }


    @Override
    public Serializable pkVal() {
        return this.brandId;
    }

}
