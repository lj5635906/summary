package com.summary.component.exception;

import com.summary.common.core.dto.R;
import com.summary.common.core.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import java.text.MessageFormat;
import java.util.List;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;

/**
 * 全局异常处理器
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@ControllerAdvice
public class CustomExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    public CustomExceptionHandler() {
        LOG.info("*************************** 全局异常处理器 *******************************");
    }

    /**
     * 处理自定义异常
     *
     * @param ex 自定义异常
     * @return data
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public R<String> handleCustomException(CustomException ex) {
        LOG.error(ex.getMessage(), ex);
        return generateResultVo(ex.getCode(), ex.getMessage(), ex.getArgs());
    }

    /**
     * 处理 HttpMessageNotReadableException
     *
     * @param ex HttpMessageNotReadableException
     * @return 错误信息
     */
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        LOG.error(ex.getMessage(), ex);
        return R.badRequest();
    }


    /**
     * 处理 HttpRequestMethodNotSupportedException
     *
     * @param ex HttpRequestMethodNotSupportedException
     * @return 错误信息
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        LOG.error(ex.getMessage(), ex);
        return R.custom(UNSUPPORTED_MEDIA_TYPE.getCode(), UNSUPPORTED_MEDIA_TYPE.getMessage());
    }


    /**
     * get方法 参数错误
     * 处理 MethodArgumentTypeMismatchException
     *
     * @param ex MethodArgumentTypeMismatchException
     * @return 错误信息
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleMissingServletRequestParameterException(MethodArgumentTypeMismatchException ex) {
        LOG.error(ex.getMessage(), ex);
        String message = "[" + ex.getName() + "] - [" + ex.getParameter() + "] 参数错误";
        return R.custom(BAD_REQUEST.getCode(), message);
    }

    /**
     * get方法 参数错误
     * 处理 MissingServletRequestParameterException
     *
     * @param ex MissingServletRequestParameterException
     * @return 错误信息
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        LOG.error(ex.getMessage(), ex);
        String message = "[" + ex.getParameterName() + "] - [" + ex.getParameterType() + "] 参数错误";
        return R.custom(BAD_REQUEST.getCode(), message);
    }


    /**
     * get方法 参数错误
     * 处理 BindException
     *
     * @param ex BindException
     * @return 错误信息
     */
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleBindException(BindException ex) {
        LOG.error(ex.getMessage(), ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String message = "[" + fieldError.getField() + "] 参数错误";
            return R.custom(BAD_REQUEST.getCode(), message);
        }
        return R.custom(BAD_REQUEST.getCode(), BAD_REQUEST.getMessage());
    }


    /**
     * 文件上传 参数错误
     * 处理 MultipartException
     *
     * @param ex MultipartException
     * @return 错误信息
     */
    @ExceptionHandler({MultipartException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleMultipartException(MultipartException ex) {
        LOG.error(ex.getMessage(), ex);
        String message = "[file/files] - [MultipartFile] 参数错误";
        return R.custom(BAD_REQUEST.getCode(), message);
    }

    /**
     * 404 异常
     * 处理 MultipartException
     *
     * @param ex MultipartException
     * @return 错误信息
     */
//    @ExceptionHandler({NoResourceFoundException.class})
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public R<String> handleNoResourceFoundException(NoResourceFoundException ex) {
//        LOG.error(ex.getMessage(), ex);
//        return R.custom(NOT_FOUND.getCode(), NOT_FOUND.getMessage());
//    }

    /**
     * post方法 参数错误
     * 处理 MethodArgumentNotValidException
     *
     * @param ex MethodArgumentNotValidException
     * @return 错误信息
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public R<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        LOG.error(ex.getMessage(), ex);
        BindingResult errors = ex.getBindingResult();
        List<ObjectError> objectErrors = errors.getAllErrors();
        if (!CollectionUtils.isEmpty(objectErrors)) {
            for (ObjectError error : objectErrors) {
                FieldError fieldError = (FieldError) error;
                String message = "[" + fieldError.getField() + "]" + error.getDefaultMessage();
                return R.custom(BAD_REQUEST.getCode(), message);
            }
        }
        return R.badRequest();
    }

    /**
     * 处理默认Exception
     *
     * @param ex Exception
     * @return ResultVo
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public R<String> handleException(Exception ex) {
        LOG.error(ex.getMessage(), ex);
        return R.serverError();
    }

    /**
     * 生成“R”返回对象。
     *
     * @param code    错误代码
     * @param message 错误信息
     * @param args    错误信息中包含的占位符所使用的参数
     * @return 生成好的“RestApiErrorResponse”
     */
    protected R<String> generateResultVo(Integer code, String message, Object[] args) {
        if (null != args && args.length > 0) {
            return R.custom(code, MessageFormat.format(message, args));
        }
        return R.custom(code, message);
    }
}
