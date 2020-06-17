package com.junzijian.framework.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.junzijian.framework.common.model.response.code.CommonCode;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * 通用Result
 *
 * @author bebopze
 * @date 2018/11/20
 */
@Data
public class ResultBean<T> extends ResponseResult {

    /**
     * 数据明细
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    /**
     * 为null时,不参与序列化
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalNum;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageIndex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;


    public static <T> ResultBean<T> ofSuccess() {
        return of(null, true, CommonCode.SUCCESS);
    }

    public static <T> ResultBean<T> ofSuccess(T data) {
        return of(data, true, CommonCode.SUCCESS);
    }

    public static <T> ResultBean<T> ofSuccess(T data, String msg) {
        return of(data, true, CommonCode.SUCCESS.code(), msg);
    }

    public static <T> ResultBean<T> ofSuccess(T data, CommonCode commonCode) {
        return of(data, true, commonCode);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, true, CommonCode.SUCCESS, totalNum, pageIndex, pageSize);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {
        return of(data, true, CommonCode.SUCCESS, totalNum, pageIndex, pageSize, msg);
    }

    public static <T> ResultBean<T> of(T data, boolean success, CommonCode commonCode,
                                       Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, success, commonCode, totalNum, pageIndex, pageSize, null);
    }

    public static <T> ResultBean of(T data, boolean success, CommonCode commonCode) {
        return of(data, success, commonCode.code(), commonCode.msg());
    }

    public static <T> ResultBean of(T data, boolean success, Integer code, String msg) {
        ResultBean resultBean = new ResultBean<>();
        resultBean.setData(data);
        resultBean.setSuccess(success);
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        if (data instanceof Collection) {
            resultBean.setTotalNum(((Collection) data).size());
        }
        return resultBean;
    }

    public static <T> ResultBean<T> of(T data, boolean success, CommonCode commonCode,
                                       Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {

        ResultBean resultBean = new ResultBean();
        resultBean.setData(data);
        resultBean.setSuccess(success);
        resultBean.setCode(commonCode.code());
        resultBean.setMsg(commonCode.msg());
        if (data instanceof Collection && null == totalNum) {
            resultBean.setTotalNum(((Collection) data).size());
        }
        resultBean.setTotalNum(totalNum);
        resultBean.setPageIndex(pageIndex);
        resultBean.setPageSize(pageSize);
        resultBean.setTotalPage((null == pageSize || pageSize == 0) ? null : (totalNum % pageSize == 0 ? totalNum / pageSize : (totalNum / pageSize + 1)));
        resultBean.setMsg(msg);
        return resultBean;
    }


    public static <T> ResultBean<T> ofError(CommonCode commonCode) {
        return ofError(commonCode.code(), commonCode.msg());
    }

    public static <T> ResultBean<T> ofError(String msg) {
        return ofError(CommonCode.FAIL.code(), msg);
    }

    public static <T> ResultBean<T> ofError(int code, String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(false);
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        return resultBean;
    }

    public static <T> ResultBean<List<T>> ofPageInfo(PageInfo<T> pageInfo) {
        return ofSuccess(pageInfo.getList(), (int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
