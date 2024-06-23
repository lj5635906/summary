package com.summary.client.remote;

import com.summary.client.goods.dto.CategoryTreeDTO;

import java.util.List;

/**
 * 分类相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
public interface CategoryRemoteService {

    /**
     * 获取分类树
     *
     * @return .
     */
    List<CategoryTreeDTO> getCategoryTree();
}
