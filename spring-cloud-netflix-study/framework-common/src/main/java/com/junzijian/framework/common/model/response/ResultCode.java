package com.junzijian.framework.common.model.response;

/**
 * 10000-- 通用错误代码
 * 22000-- 媒资错误代码
 * 23000-- 用户中心错误代码
 * 24000-- cms错误代码
 * 25000-- 文件系统
 *
 * @author liuzhe
 * @date 2018/11/13
 */
public interface ResultCode {

    /**
     * 操作是否成功,true为成功，false操作失败
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
    String message();
}
