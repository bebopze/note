package com.junzijian.framework.common.exception;

import com.google.common.collect.ImmutableMap;
import com.junzijian.framework.common.model.response.CommonCode;
import com.junzijian.framework.common.model.response.ResponseResult;
import com.junzijian.framework.common.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 *
 * @author liuzhe
 * @date 2018/11/14
 */
@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    /**
     * 定义map，配置异常类型所对应的错误代码
     */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    /**
     * 定义map的builder对象，去构建ImmutableMap
     */
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();


    /**
     * 捕获CustomException此类异常
     *
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException) {

        //记录日志
        log.error("catch exception : {}", customException.getMessage());

        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }


    /**
     * 捕获Exception此类异常
     *
     * @param exception
     * @return
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
    public ResponseResult exception(Exception exception) {

        //记录日志
        log.error("catch exception:{}", exception.getMessage());
        if (EXCEPTIONS == null) {
            // EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }

        // 从EXCEPTIONS中找异常类型所对应的错误代码，如果找到了将错误代码响应给用户，如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            // 返回99999异常
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static {
        // 定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }
}
