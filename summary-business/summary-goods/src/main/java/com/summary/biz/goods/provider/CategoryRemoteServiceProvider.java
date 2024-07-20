package com.summary.biz.goods.provider;

import com.summary.biz.goods.service.CategoryService;
import com.summary.client.goods.dto.CategoryTreeDTO;
import com.summary.client.remote.CategoryRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@RestController
@RequestMapping("/goods/category")
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
