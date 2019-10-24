package com.junzijian.framework.common.exception;

import com.junzijian.framework.common.model.response.code.CommonCode;
import com.junzijian.framework.common.model.response.code.ResultCode;
import lombok.Data;

/**
 * @author liuzhe
 * @date 2019/10/24
 */
@Data
public class CustomException extends RuntimeException {

    private int code = CommonCode.FAIL.code();

    private String msg;


    public CustomException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CustomException(ResultCode resultCode) {
        super(resultCode.msg());
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public CustomException(Throwable cause) {
        super(cause);
        this.msg = cause.getMessage();
    }

    public CustomException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public CustomException(ResultCode resultCode, Throwable cause) {
        super(resultCode.msg(), cause);
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }
}