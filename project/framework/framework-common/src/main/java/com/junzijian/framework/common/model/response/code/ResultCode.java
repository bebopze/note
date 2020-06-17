package com.junzijian.framework.common.model.response.code;

/**
 * 10xxx -- 通用-错误代码
 * 11xxx -- 媒资-错误代码
 * 12xxx -- 用户中心-错误代码
 * 13xxx -- cms-错误代码
 * 14xxx -- 文件系统-错误代码
 *
 * @author bebopze
 * @date 2018/11/13
 */
public interface ResultCode {

    /**
     * 操作是否成功
     *
     * @return
     */
    boolean success();

    /**
     * 操作代码
     *
     * @return
     */
    int code();

    /**
     * 提示信息
     *
     * @return
     */
    String msg();
}
