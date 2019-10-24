package com.junzijian.framework.common.aspect;

import com.alibaba.fastjson.JSONPathException;
import com.fasterxml.jackson.core.JsonParseException;
import com.junzijian.framework.common.model.response.ResponseResult;
import com.junzijian.framework.common.model.response.code.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Set;

/**
 * 统一异常处理
 *
 * @author liuzhe
 * @date 2018-12-26
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseResult exceptionHandler(Throwable e) {

        log.error(e.getMessage(), e);

        if (e instanceof ConstraintViolationException) {

            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException) e).getConstraintViolations();
            ConstraintViolation<?> violation = violations.iterator().next();

            String message = violation.getMessage();
            return ResponseResult.FAIL(CommonCode.INVALID_PARAM.code(), message);

        } else if (e instanceof MethodArgumentNotValidException) {

            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            FieldError fieldError = bindingResult.getFieldErrors().get(0);

            String msg = fieldError.getField() + ":" + fieldError.getDefaultMessage();
            return ResponseResult.FAIL(CommonCode.INVALID_PARAM.code(), msg);

        } else if (e instanceof MissingServletRequestParameterException) {

            String parameterName = ((MissingServletRequestParameterException) e).getParameterName();
            return ResponseResult.FAIL(CommonCode.INVALID_PARAM.code(), parameterName + "不能为空");

        } else if (e instanceof NumberFormatException) {
            return ResponseResult.FAIL("参数类型转换异常");
        } else if (e instanceof IllegalArgumentException) {
            return ResponseResult.FAIL(e.getMessage());
        } else if (e instanceof NullPointerException) {
            return ResponseResult.FAIL(e.getMessage());
        } else if (e instanceof InvocationTargetException) {
            return ResponseResult.FAIL(e.getCause().getMessage());
        } else if (e instanceof HttpMessageNotReadableException) {
            return ResponseResult.FAIL(e.getMessage());
        } else if (e instanceof JsonParseException) {
            return ResponseResult.FAIL("参数类型转换异常");
        } else if (e instanceof JSONPathException) {
            return ResponseResult.FAIL("类型转换异常");
        } else if (e instanceof SQLException) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else if (e instanceof RuntimeException) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else if (e instanceof HttpRequestMethodNotSupportedException) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.error(request.getServletPath() + " " + e.getMessage());

            return ResponseResult.FAIL(e.getMessage());

        } else if (e instanceof Exception) {
            return ResponseResult.FAIL("出错啦,请稍后再试!");
        } else {
            String errorMsg = e.toString() == null ? e.getMessage() : e.toString();
            return ResponseResult.FAIL(!StringUtils.hasText(errorMsg) ? "未知错误" : errorMsg);
        }
    }
}