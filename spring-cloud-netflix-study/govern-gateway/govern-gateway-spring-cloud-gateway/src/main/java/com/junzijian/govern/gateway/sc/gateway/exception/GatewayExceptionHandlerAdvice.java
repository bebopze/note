package com.junzijian.govern.gateway.sc.gateway.exception;

import com.junzijian.framework.common.model.response.ResponseResult;
import io.netty.channel.ConnectTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
//@Component
public class GatewayExceptionHandlerAdvice {

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseResult handle(ResponseStatusException ex) {
        log.error("response status exception:{}", ex.getMessage());
        return ResponseResult.FAIL();
    }

    @ExceptionHandler(value = {ConnectTimeoutException.class})
    public ResponseResult handle(ConnectTimeoutException ex) {
        log.error("connect timeout exception:{}", ex.getMessage());
        return ResponseResult.FAIL();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseResult handle(NotFoundException ex) {
        log.error("not found exception:{}", ex.getMessage());
        return ResponseResult.FAIL();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handle(RuntimeException ex) {
        log.error("runtime exception:{}", ex.getMessage());
        return ResponseResult.FAIL();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handle(Exception ex) {
        log.error("exception:{}", ex.getMessage());
        return ResponseResult.FAIL();
    }

    @ExceptionHandler(value = {Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseResult handle(Throwable throwable) {
        ResponseResult result = ResponseResult.FAIL();
        if (throwable instanceof ResponseStatusException) {
            result = handle((ResponseStatusException) throwable);
        } else if (throwable instanceof ConnectTimeoutException) {
            result = handle((ConnectTimeoutException) throwable);
        } else if (throwable instanceof NotFoundException) {
            result = handle((NotFoundException) throwable);
        } else if (throwable instanceof RuntimeException) {
            result = handle((RuntimeException) throwable);
        } else if (throwable instanceof Exception) {
            result = handle((Exception) throwable);
        }
        return result;
    }
}
