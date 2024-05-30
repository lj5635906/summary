package com.summary.common.core.page;

import com.summary.common.core.utils.ConvertUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * spring-data 分页转换器
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class PageConvertUtils {

    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageInfo PageInfo
     * @param pageable 页码信息
     * @param <T>      T
     * @return PageResult<T>
     */
    public static <T> PageResult<T> convertPageResult(Page<T> pageInfo, Pageable pageable) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum(pageable.getPageNumber() + 1);
        pageResult.setPageSize(pageable.getPageSize());
        pageResult.setTotal(pageInfo.getTotalElements());
        pageResult.setTotalPages(pageInfo.getTotalPages());
        pageResult.setContent(pageInfo.getContent());
        return pageResult;
    }

    /**
     * 将分页信息封装到统一的接口
     *
     * @param pageInfo PageInfo
     * @param dest     转换后的类型
     * @param pageable 页码信息
     * @param <S>      转换前类型
     * @param <T>      转换后类型
     * @return PageResult<T>
     */
    public static <S, T> PageResult<T> convertPageResult(Page<S> pageInfo, Class<T> dest, Pageable pageable) {
        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum(pageable.getPageNumber() + 1);
        pageResult.setPageSize(pageable.getPageSize());
        pageResult.setTotal(pageInfo.getTotalElements());
        pageResult.setTotalPages(pageInfo.getTotalPages());
        List<S> list = pageInfo.getContent();
        if (!CollectionUtils.isEmpty(list)) {
            pageResult.setContent(ConvertUtils.convertList(pageInfo.getContent(), dest));
        }
        return pageResult;
    }

    /**
     * 将分页信息封装到统一的接口
     *
     * @param content  集合内容
     * @param dest     转换后的类型
     * @param pageable 页码信息
     * @param total    总条数
     * @param <S>      转换前类型
     * @param <T>      转换后类型
     * @return PageResult<T>
     */
    public static <S, T> PageResult<T> convertPageResult(List<S> content, Class<T> dest, Pageable pageable, long total) {
        // 页大小
        final int pageSize = pageable.getPageSize();
        // 页码
        final int pageNum = pageable.getPageNumber() + 1;
        // 页数余数
        int totalPageRemainder = (int) (total % pageSize);

        PageResult<T> pageResult = new PageResult<T>();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        pageResult.setTotal(total);
        pageResult.setTotalPages((totalPageRemainder == 0 ? (int) (total / pageSize) : (int) (total / pageSize) + 1));
        if (!CollectionUtils.isEmpty(content)) {
            pageResult.setContent(ConvertUtils.convertList(content, dest));
        }
        return pageResult;
    }
}
