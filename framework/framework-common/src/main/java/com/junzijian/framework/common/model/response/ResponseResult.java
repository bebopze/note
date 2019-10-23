package com.junzijian.framework.common.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2018/11/13
 */
@Data
@NoArgsConstructor
public class ResponseResult implements Response, Serializable {

    private static final long serialVersionUID = -2361820086956983473L;

    /**
     * 操作是否成功
     */
    boolean success = SUCCESS;

    /**
     * 操作代码
     */
    int code = SUCCESS_CODE;

    /**
     * 提示信息
     */
    String msg;


    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }

    public static ResponseResult FAIL(String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setCode(CommonCode.FAIL.code);
        responseResult.setMsg(msg);
        return responseResult;
    }

}
