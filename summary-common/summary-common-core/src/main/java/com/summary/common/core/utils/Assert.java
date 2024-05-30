package com.summary.common.core.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.exception.code.CustomCodeService;

import java.util.Collection;
import java.util.Map;

/**
 * 自定义断言
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class Assert {

    /**
     * 断言布尔表达式，如果表达式求值为 true，则抛出 CustomException
     *
     * @param expression 布尔表达式
     * @param errorCode  全局错误枚举 com.ajzx.common.core.exception.code 包下枚举
     * @throws CustomException 如果表达式为 true
     */
    public static void isTrue(boolean expression, CustomCodeService errorCode) {
        if (expression) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言布尔表达式，如果表达式求值为 true，则抛出 CustomException
     *
     * @param expression 布尔表达式
     * @param exception  {@link CustomException}
     * @throws CustomException 如果表达式为 true
     */
    public static void isTrue(boolean expression, CustomException exception) {
        if (expression) {
            throw exception;
        }
    }


    /**
     * 断言布尔表达式，如果表达式求值为 false，则抛出 CustomException
     *
     * @param expression 布尔表达式
     * @param errorCode  全局错误枚举 com.ajzx.common.core.exception.code 包下枚举
     * @throws CustomException 如果表达式为 false
     */
    public static void isFalse(boolean expression, CustomCodeService errorCode) {
        if (!expression) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言布尔表达式，如果表达式求值为 false，则抛出 CustomException
     *
     * @param expression 布尔表达式
     * @param exception  {@link CustomException}
     * @throws CustomException 如果表达式为 false
     */
    public static void isFalse(boolean expression, CustomException exception) {
        if (!expression) {
            throw exception;
        }
    }

    /**
     * 断言布尔表达式，如果表达式求值为 true，则抛出 CustomException
     *
     * @param str       字符串
     * @param errorCode 全局错误枚举 com.ajzx.common.core.exception.code 包下枚举
     * @throws CustomException 如果表达式为 true
     */
    public static void isBlank(String str, CustomCodeService errorCode) {
        if (StrUtil.isBlank(str)) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言布尔表达式，如果表达式求值为 true，则抛出 CustomException
     *
     * @param str       字符串
     * @param exception {@link CustomException}
     * @throws CustomException 如果表达式为 true
     */
    public static void isBlank(String str, CustomException exception) {
        if (StrUtil.isBlank(str)) {
            throw exception;
        }
    }


    /**
     * 断言对象为 null
     *
     * @param object    要检查的对象
     * @param errorCode 全局错误枚举
     * @throws CustomException 如果对象为null
     */
    public static void isNull(Object object, CustomCodeService errorCode) {
        if (ObjectUtil.isNull(object)) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言对象为 null
     *
     * @param object    要检查的对象
     * @param exception {@link CustomException}
     * @throws CustomException 如果对象为null
     */
    public static void isNull(Object object, CustomException exception) {
        if (ObjectUtil.isNull(object)) {
            throw exception;
        }
    }

    /**
     * 断言对象不为 null
     *
     * @param object    要检查的对象
     * @param errorCode 全局错误枚举
     * @throws CustomException 如果对象为null
     */
    public static void notNull(Object object, CustomCodeService errorCode) {
        if (ObjectUtil.isNotNull(object)) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言对象不为 null
     *
     * @param object    要检查的对象
     * @param exception {@link CustomException}
     * @throws CustomException 如果对象为null
     */
    public static void notNull(Object object, CustomException exception) {
        if (ObjectUtil.isNotNull(object)) {
            throw exception;
        }
    }


    /**
     * 断言数组
     *
     * @param array     要检查的数组
     * @param errorCode 全局错误枚举
     * @throws CustomException 如果对象数组为null或不包含任何元素
     */
    public static void isEmpty(Object[] array, CustomCodeService errorCode) {
        if (ArrayUtil.isEmpty(array)) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言数组
     *
     * @param array     要检查的数组
     * @param exception {@link CustomException}
     * @throws CustomException 如果对象数组为null或不包含任何元素
     */
    public static void isEmpty(Object[] array, CustomException exception) {
        if (ArrayUtil.isEmpty(array)) {
            throw exception;
        }
    }

    /**
     * 断言集合
     *
     * @param collection 要检查的集合
     * @param errorCode  全局错误枚举
     * @throws CustomException 如果对象集合为null或不包含任何元素
     */
    public static void isEmpty(Collection<?> collection, CustomCodeService errorCode) {
        if (collection == null || collection.isEmpty()) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言集合
     *
     * @param collection 要检查的集合
     * @param exception  {@link CustomException}
     * @throws CustomException 如果对象集合为null或不包含任何元素
     */
    public static void isEmpty(Collection<?> collection, CustomException exception) {
        if (collection == null || collection.isEmpty()) {
            throw exception;
        }
    }

    /**
     * 断言Map
     *
     * @param map       要检查的map
     * @param errorCode 全局错误枚举
     * @throws CustomException 如果Map为null或不包含任何元素
     */
    public static void isEmpty(Map<?, ?> map, CustomCodeService errorCode) {
        if (null == map || map.isEmpty()) {
            throw new CustomException(errorCode);
        }
    }

    /**
     * 断言Map
     *
     * @param map       要检查的map
     * @param exception {@link CustomException}
     * @throws CustomException 如果Map为null或不包含任何元素
     */
    public static void isEmpty(Map<?, ?> map, CustomException exception) {
        if (null == map || map.isEmpty()) {
            throw exception;
        }
    }
}
