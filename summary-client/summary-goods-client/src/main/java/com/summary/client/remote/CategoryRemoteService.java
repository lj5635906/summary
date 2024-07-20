package com.summary.client.remote;

import com.summary.client.goods.dto.CategoryTreeDTO;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

/**
 * 分类相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@FeignClient(value = "summary-goods", path = "/goods/category")
public interface CategoryRemoteService {

    /**
     * 获取分类树
     *
     * @return .
     */
    List<CategoryTreeDTO> getCategoryTree();
}
