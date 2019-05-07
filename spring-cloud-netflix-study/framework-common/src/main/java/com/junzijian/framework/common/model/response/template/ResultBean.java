package com.junzijian.framework.common.model.response.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import com.junzijian.framework.common.model.response.ResponseResult;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * @author liuzhe
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
        return of(null, true, ExceptionEnum.EC00000200);
    }

    public static <T> ResultBean<T> ofSuccess(T data) {
        return of(data, true, ExceptionEnum.EC00000200);
    }

    public static <T> ResultBean<T> ofSuccess(T data, String msg) {
        return of(data, true, ExceptionEnum.EC00000200.getCode(), msg);
    }

    public static <T> ResultBean<T> ofSuccess(T data, ExceptionEnum exceptionEnum) {
        return of(data, true, exceptionEnum);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, true, ExceptionEnum.EC00000200, totalNum, pageIndex, pageSize);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {
        return of(data, true, ExceptionEnum.EC00000200, totalNum, pageIndex, pageSize, msg);
    }

    public static <T> ResultBean<T> of(T data, boolean success, ExceptionEnum exceptionEnum,
                                       Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, success, exceptionEnum, totalNum, pageIndex, pageSize, null);
    }

    public static <T> ResultBean of(T data, boolean success, ExceptionEnum exceptionEnum) {
        if (null != exceptionEnum) {
            return of(data, success, exceptionEnum.getCode(), exceptionEnum.getMessage());
        } else {
            return of(data, success, null, null);
        }
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

    public static <T> ResultBean<T> of(T data, boolean success, ExceptionEnum exceptionEnum,
                                       Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {

        ResultBean resultBean = new ResultBean();
        resultBean.setData(data);
        resultBean.setSuccess(success);
        if (null != exceptionEnum) {
            resultBean.setCode(exceptionEnum.getCode());
            resultBean.setMsg(exceptionEnum.getMessage());
        }
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


    public static <T> ResultBean<T> ofError(ExceptionEnum exceptionEnum) {
        return ofError(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public static <T> ResultBean<T> ofError(String msg) {
        return ofError(ExceptionEnum.EC00000500.getCode(), msg);
    }

    public static <T> ResultBean<T> ofError(int code, String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(false);
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        resultBean.setData(null);
        return resultBean;
    }

    public static <T> ResultBean<List<T>> ofPageInfo(PageInfo<T> pageInfo) {
        return ofSuccess(pageInfo.getList(), (int) pageInfo.getTotal(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
