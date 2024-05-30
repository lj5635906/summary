package com.summary.common.core.exception.code;

/**
 * 异常码枚举 接口
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public interface CustomCodeService {

    /**
     * 获取 错误码
     *
     * @return 错误代码
     */
    Integer getCode();

    /**
     * 获取 错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 获取 错误信息中的补位用的对应的值
     *
     * @return 错误信息中的补位用的对应的值
     */
    Object[] getArgs();
}
