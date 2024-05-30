package com.summary.component.repository.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据操作者基础字段
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Getter
@Setter
public abstract class BaseOperatorDO<T> extends BaseDO<BaseOperatorDO<T>> {

    /*** 创建人id */
    private Long creatorId;
    /*** 创建人姓名 */
    private String creatorName;
    /*** 最后一次操作人id */
    private Long operatorId;
    /*** 最后一次操作人名称 */
    private String operatorName;

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        this.operatorId = creatorId;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        this.operatorName = creatorName;
    }
}

