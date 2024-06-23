package com.summary.biz.goods.provider;

import com.summary.biz.goods.service.CategoryService;
import com.summary.client.goods.dto.CategoryTreeDTO;
import com.summary.client.remote.CategoryRemoteService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 分类相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@DubboService
public class CategoryRemoteServiceProvider implements CategoryRemoteService {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取分类树
     *
     * @return .
     */
    @Override
    public List<CategoryTreeDTO> getCategoryTree() {
        return categoryService.getCategoryTree();
    }
}
