package com.summary.biz.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 分类
 *
 * @author myabtis-plus
 * @since 2024-06-23
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("category")
public class CategoryDO extends BaseDO<CategoryDO> {

    private static final long serialVersionUID = 1L;
    /*** 分类id */
    @TableId(value = "category_id", type = IdType.AUTO)
    private Long categoryId;
    /*** 上级分类id */
    private Long parentId;
    /*** 分类名称 */
    private String categoryName;
    /*** 显示标志(0/false-不显示,1/true-显示) */
    private Boolean showFlag;
    /*** 排序 */
    private Integer sort;

    @Builder
    public CategoryDO(
            Integer version,
            LocalDateTime createTime,
            LocalDateTime updateTime,
            Boolean deleteFlag,
            Long categoryId,
            Long parentId,
            String categoryName,
            Boolean showFlag,
            Integer sort
    ) {
        super(version, createTime, updateTime, deleteFlag);
        this.categoryId = categoryId;
        this.parentId = parentId;
        this.categoryName = categoryName;
        this.showFlag = showFlag;
        this.sort = sort;
    }


    @Override
    public Serializable pkVal() {
        return this.categoryId;
    }

}
