package com.junzijian.framework.common.model.response.template;

/**
 * @author bebop
 * @date 2018/1/11
 */
public enum ExceptionEnum {

    NOT_LOGIN(10001, "操作会话已失效，请重新登录！"),
    NOT_PERMISSION(401, "您无该权限"),

    EC00000200(200, "success"),
    EC00000500(500, "系统异常");


    private int code;

    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
