package com.bebopze.framework.common.aspect;

import com.alibaba.fastjson.JSONPathException;
import com.fasterxml.jackson.core.JsonParseException;
import com.bebopze.framework.common.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;


/**
 * 统一异常处理
 *
 * @author bebopze
 * @date 2018-12-26
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseResult exceptionHandler(Throwable e) {

        return doGlobalExceptionHandler(e);
    }

    /**
     * 执行异常处理
     *
     * @param e
     * @return
     */
    public static ResponseResult doGlobalExceptionHandler(Throwable e) {

        log.error(e.getMessage(), e);

        /*if (e instanceof BizException) {
            String code = ((BizException) e).getCode();
            String msg = ((BizException) e).getMsg();
            if (null == code) {
                return ResponseResult.FAIL(msg);
            } else {
                return ResponseResult.FAIL(code, msg);
            }
        } else */
        if (e instanceof MissingServletRequestParameterException) {
            String parameterName = ((MissingServletRequestParameterException) e).getParameterName();
            return ResponseResult.FAIL(parameterName + "不能为空");
        } else if (e instanceof MethodArgumentNotValidException) {
            return ResponseResult.FAIL("表单必录数据填写不完整");
        }/* else if (e instanceof ConstraintViolationException) {
            return ResponseResult.FAIL(e.getMessage());
        } */ else if (e instanceof IllegalArgumentException) {
            return ResponseResult.FAIL(e.getMessage());
        } else if (e instanceof NullPointerException) {
            return ResponseResult.FAIL(e.getMessage());
        } else if (e instanceof HttpMessageNotReadableException) {
            return ResponseResult.FAIL(e.getMessage());
        } /*else if (e instanceof MailException) {
            return ResponseResult.FAIL("邮件发送失败");
        } else if (e instanceof ActivitiException) {
            return ResponseResult.FAIL("流程审核参数有误");
        }*/ else if (e instanceof NumberFormatException) {
            return ResponseResult.FAIL("参数类型转换异常");
        } else if (e instanceof JsonParseException) {
            return ResponseResult.FAIL("参数类型转换异常");
        } else if (e instanceof JSONPathException) {
            return ResponseResult.FAIL("类型转换异常");
        } else if (e instanceof SQLException) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else if (e instanceof RuntimeException) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else if (e instanceof Exception) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else {
            String errorMsg = e.toString() == null ? e.getMessage() : e.toString();
            return ResponseResult.FAIL(StringUtils.isEmpty(errorMsg) ? "未知错误" : errorMsg);
        }
    }

}
