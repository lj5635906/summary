package com.summary.common.core.page;

import lombok.Data;

/**
 * 分页请求参数
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
}
