package com.summary.client.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@Data
public class CategoryTreeDTO implements Serializable {
    /*** 分类id */
    private Long categoryId;
    /*** 上级分类id */
    private Long parentId;
    /*** 分类名称 */
    private String categoryName;
    /*** 显示标志(0/false-不显示,1/true-显示) */
    private Boolean showFlag;
    /*** 排序 */
    private Integer sort;
    /*** 创建时间*/
    private LocalDateTime createTime;
    /*** 更新时间*/
    private LocalDateTime updateTime;
    /*** 子节点*/
    private List<CategoryTreeDTO> children;
}
