package com.junzijian.framework.common.model.response.code;

import lombok.ToString;

/**
 * 10xxx - 通用错误代码
 *
 * @author bebopze
 * @date 2018/11/13
 */
@ToString
public enum CommonCode implements ResultCode {


    // ----------------- 10xxx -- 通用错误代码
    SUCCESS(true, 10000, "操作成功！"),
    FAIL(false, 10001, "操作失败！"),

    INVALID_PARAM(false, 10002, "非法参数！"),
    INVALID_ACCESS(false, 10003, "非法访问！"),
    REQ_TIMEOUT(false, 10004, "请求超时！"),
    NOT_LOGIN(false, 10005, "此操作需要登陆！"),
    UN_AUTHORISE(false, 10006, "权限不足，无权操作！"),
    LIMIT_ERROR(false, 10007, "操作太过频繁，请勿重复提交！"),
    SERVER_ERROR(false, 10008, "抱歉，系统繁忙，请稍后重试！");


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
    public String msg() {
        return msg;
    }

}
