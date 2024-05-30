package com.summary.component.repository.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.summary.common.core.page.PageResult;
import com.summary.common.core.utils.ConvertUtils;

import java.util.List;

/**
 * 分页数据转换
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class PageUtils {

    /**
     * 将分页信息封装到统一的接口
     *
     * @param page {@link Page}
     * @return PageResult<T>
     */
    public static <T> PageResult<T> convertPageResult(Page<T> page) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setTotalPages((int) page.getPages());
        pageResult.setContent(page.getRecords());
        return pageResult;
    }

    /**
     * 将分页信息封装到统一的接口
     *
     * @param page {@link Page}
     * @return PageResult<T>
     */
    public static <S, T> PageResult<T> convertPageResult(Page<S> page, Class<T> dest) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setTotalPages((int) page.getPages());
        List<S> list = page.getRecords();
        if (null != list && !list.isEmpty()) {
            pageResult.setContent(ConvertUtils.convertList(list, dest));
        }
        return pageResult;
    }

    /**
     * 将分页信息封装到统一的接口
     *
     * @param page       {@link Page}
     * @param targetList content
     * @return PageResult<T>
     */
    public static <S, T> PageResult<T> convertPageResult(Page<S> page, List<T> targetList) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum((int) page.getCurrent());
        pageResult.setPageSize((int) page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setTotalPages((int) page.getPages());
        if (null != targetList && !targetList.isEmpty()) {
            pageResult.setContent(targetList);
        }
        return pageResult;
    }
}
