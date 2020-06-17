package com.junzijian.framework.common.model.response;

import com.junzijian.framework.common.model.response.code.CommonCode;
import com.junzijian.framework.common.model.response.code.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * base Response Result
 *
 * @author bebop
 * @date 2018/11/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResult implements Serializable {

    private static final long serialVersionUID = -2361820086956983473L;


    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String msg;


    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public static ResponseResult FAIL() {
        return new ResponseResult(CommonCode.FAIL);
    }

    public static ResponseResult FAIL(String msg) {
        return FAIL(CommonCode.FAIL.code(), msg);
    }

    public static ResponseResult FAIL(int code, String msg) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSuccess(false);
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        return responseResult;
    }
}
