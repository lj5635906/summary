package com.summary.front.web.rest.goods;

import com.summary.client.goods.dto.CategoryTreeDTO;
import com.summary.client.goods.dto.GoodsDTO;
import com.summary.client.goods.param.CreateGoodsParam;
import com.summary.client.remote.CategoryRemoteService;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.dto.R;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

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

    @DubboReference
    private CategoryRemoteService categoryRemoteService;

    /**
     * 获取分类树
     *
     * @return 分类树
     */
    @PostMapping("/getCategoryTree")
    public R<List<CategoryTreeDTO>> getCategoryTree() {
        return R.success(categoryRemoteService.getCategoryTree());
    }

}
