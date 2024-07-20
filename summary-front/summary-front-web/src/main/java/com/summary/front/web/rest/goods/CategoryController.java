package com.summary.front.web.rest.goods;

import com.summary.client.goods.dto.CategoryTreeDTO;
import com.summary.client.remote.CategoryRemoteService;
import com.summary.common.core.dto.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 类目相关接口
 *
 * @author jie.luo
 * @since 2024/6/12
 */
@RestController
@RequestMapping("/web/category")
public class CategoryController {

    @Autowired
    private CategoryRemoteService categoryRemoteService;

    /**
     * 获取分类树
     *
     * @return 分类树
     */
    @GetMapping("/getCategoryTree")
    public R<List<CategoryTreeDTO>> getCategoryTree() {
        return R.success(categoryRemoteService.getCategoryTree());
    }

}
