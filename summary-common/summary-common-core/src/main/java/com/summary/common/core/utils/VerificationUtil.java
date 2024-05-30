package com.summary.common.core.utils;

import cn.hutool.core.util.ObjectUtil;
import com.summary.common.core.dto.R;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.exception.code.CustomCodeService;

import static com.summary.common.core.exception.code.BaseExceptionCode.REQUEST_ERROR;
import static com.summary.common.core.exception.code.BaseExceptionCode.SUCCESS;

/**
 * 请求验证工具
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class VerificationUtil {


    /**
     * 验证请求是否成功
     *
     * @param r   ResultDTO<T>
     * @param <T> T
     */
    public static <T> void check(R<T> r) {
        if (null == r) {
            throw new CustomException(REQUEST_ERROR);
        }

        final Integer code = r.getCode();
        final String message = r.getMessage();

        Assert.isFalse(SUCCESS.getCode().equals(code), new CustomException(code, message));
    }

    /**
     * 验证请求是否成功，同时返回请求响应数据
     * 直接返回响应数据
     *
     * @param resultDTO ResultDTO<T>
     * @param <T>       T
     * @return T
     */
    public static <T> T checkGetResponse(R<T> resultDTO) {
        check(resultDTO);
        return resultDTO.getData();
    }

    /**
     * 验证请求是否成功，同时返回请求响应数据
     * 响应数据为空抛异常，不为空则返回响应数据
     *
     * @param resultDTO   ResultDTO<T>
     * @param codeService CustomCodeService
     * @param <T>         T
     * @return T
     */
    public static <T> T checkGetResponse(R<T> resultDTO, CustomCodeService codeService) {
        check(resultDTO);
        T data = resultDTO.getData();
        if (ObjectUtil.isEmpty(resultDTO.getData())) {
            throw new CustomException(codeService);
        }
        return data;
    }
}
