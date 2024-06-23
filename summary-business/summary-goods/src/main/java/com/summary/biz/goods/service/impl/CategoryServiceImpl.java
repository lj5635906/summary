package com.summary.biz.goods.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.goods.entity.CategoryDO;
import com.summary.biz.goods.mapper.CategoryMapper;
import com.summary.biz.goods.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.authority.dto.MenuTreeDTO;
import com.summary.client.goods.dto.CategoryTreeDTO;
import com.summary.common.core.utils.ConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.summary.common.core.constant.GlobalConstant.MenuConstant.ROOT_ID;

/**
 * <p>
 * 分类 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, CategoryDO> implements CategoryService {

    @Override
    public List<CategoryTreeDTO> getCategoryTree() {
        QueryWrapper<CategoryDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CategoryDO::getDeleteFlag, false);
        List<CategoryDO> list = this.list(queryWrapper);

        if (CollUtil.isEmpty(list)) {
            return null;
        }

        List<CategoryTreeDTO> allCategories = ConvertUtils.convertList(list, CategoryTreeDTO.class);

        List<CategoryTreeDTO> roots = allCategories.stream()
                .filter(category -> (null != category.getParentId() && ROOT_ID.equals(category.getParentId())))
                .sorted(Comparator.comparing(CategoryTreeDTO::getSort))
                .collect(Collectors.toList());
        if (CollUtil.isEmpty(roots)) {
            return null;
        }

        // 递归设置子节点
        return findChildren(roots, allCategories);
    }

    /**
     * 递归设置子节点
     *
     * @param parentCategories 父节点
     * @param allCategories    所有节点
     * @return .
     */
    private List<CategoryTreeDTO> findChildren(List<CategoryTreeDTO> parentCategories, List<CategoryTreeDTO> allCategories) {

        for (CategoryTreeDTO category : parentCategories) {
            // 获取当前节点的所有子节点
            List<CategoryTreeDTO> children = allCategories.stream()
                    .filter(categories -> category.getCategoryId().equals(categories.getParentId()))
                    .sorted(Comparator.comparing(CategoryTreeDTO::getSort))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(children)) {
                continue;
            }

            category.setChildren(findChildren(children, allCategories));
        }

        return parentCategories;
    }
}
