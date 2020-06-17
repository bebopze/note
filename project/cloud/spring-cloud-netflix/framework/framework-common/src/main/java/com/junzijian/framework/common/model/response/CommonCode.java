package com.junzijian.framework.common.model.response;

import lombok.ToString;

/**
 * @author bebop
 * @date 2018/11/13
 */
@ToString
public enum CommonCode implements ResultCode {

    INVALID_PARAM(false, 10003, "非法参数！"),
    SUCCESS(true, 10000, "操作成功！"),
    FAIL(false, 11111, "操作失败！"),
    UN_AUTHENTICATED(false, 10001, "此操作需要登陆系统！"),
    UN_AUTHORISE(false, 10002, "权限不足，无权操作！"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");

//    private static ImmutableMap<Integer, CommonCode> codes ;


    // 操作是否成功
    boolean success;

    // 操作代码
    int code;

    // 提示信息
    String msg;


    CommonCode(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return msg;
    }

}
