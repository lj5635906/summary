package com.summary.biz.goods.service;

import com.summary.biz.goods.entity.CategoryDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.goods.dto.CategoryTreeDTO;

import java.util.List;

/**
 * <p>
 * 分类 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-23
 */
public interface CategoryService extends IService<CategoryDO> {

    /**
     * 获取分类树
     *
     * @return .
     */
    List<CategoryTreeDTO> getCategoryTree();
}
