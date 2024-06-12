package com.summary.common.core.dto;

import com.summary.common.core.exception.code.BaseExceptionCode;
import lombok.Data;

import java.io.Serializable;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;

/**
 * 基础响应数据类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Data
public class R<T> implements Serializable {

    private R() {
    }

    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应提示信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;

    /**
     * 请求成功 - 无响应数据
     *
     * @return ResultVo
     */
    public static <T> R<T> success() {
        R<T> result = new R<T>();
        result.setCode(SUCCESS.getCode());
        result.setMessage(SUCCESS.getMessage());
        return result;
    }

    /**
     * 请求成功 - 有响应数据
     *
     * @param data Object
     * @return ResultVo
     */
    public static <T> R<T> success(T data) {
        R<T> result = new R<T>();
        result.setCode(SUCCESS.getCode());
        result.setMessage(SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    /**
     * 请求参数错误
     *
     * @return ResultVo
     */
    public static <T> R<T> badRequest() {
        R<T> result = new R<T>();
        result.setCode(BAD_REQUEST.getCode());
        result.setMessage(BAD_REQUEST.getMessage());
        return result;
    }

    /**
     * 服务器发生错误
     *
     * @return ResultVo
     */
    public static <T> R<T> serverError() {
        R<T> result = new R<T>();
        result.setCode(SERVER_ERROR.getCode());
        result.setMessage(SERVER_ERROR.getMessage());
        return result;
    }

    /**
     * 自定义响应数据
     *
     * @param code    响应码
     * @param message 响应提示信息
     * @return ResultVo
     */
    public static <T> R<T> custom(Integer code, String message) {
        R<T> result = new R<T>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 自定义响应数据
     *
     * @param exceptionCode {@link BaseExceptionCode}
     * @return ResultVo
     */
    public static <T> R<T> custom(BaseExceptionCode exceptionCode) {
        R<T> result = new R<T>();
        result.setCode(exceptionCode.getCode());
        result.setMessage(exceptionCode.getMessage());
        return result;
    }

    /**
     * 自定义响应数据
     *
     * @param code    响应码
     * @param message 响应提示信息
     * @param data    是否请求成功
     * @return ResultVo
     */
    public static <T> R<T> customData(Integer code, String message, T data) {
        R<T> result = new R<T>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    /**
     * 没有权限访问，请联系管理员！
     *
     * @return ResultVo
     */
    public static <T> R<T> unauthorized() {
        R<T> result = new R<T>();
        result.setCode(UNAUTHORIZED.getCode());
        result.setMessage(UNAUTHORIZED.getMessage());
        return result;
    }

    /**
     * 登陆失效，重新登陆！
     *
     * @return ResultVo
     */
    public static <T> R<T> forbidden() {
        R<T> result = new R<T>();
        result.setCode(FORBIDDEN.getCode());
        result.setMessage(FORBIDDEN.getMessage());
        return result;
    }
}
