package com.junzijian.framework.common.exception;

import com.junzijian.framework.common.model.response.ResultCode;


/**
 * 自定义异常类型
 *
 * @author liuzhe
 * @date 2018/11/14
 */
public class CustomException extends RuntimeException {

    /**
     * 错误代码
     */
    ResultCode code;

    public CustomException(ResultCode resultCode) {
        this.code = code;
    }

    public ResultCode getResultCode() {
        return code;
    }

}
