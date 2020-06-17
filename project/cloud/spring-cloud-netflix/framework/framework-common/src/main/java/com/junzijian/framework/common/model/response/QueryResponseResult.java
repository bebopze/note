package com.junzijian.framework.common.model.response;

import lombok.Data;

/**
 * @author bebopze
 * @date 2018/11/13
 */
@Data
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode, QueryResult queryResult) {
        super(resultCode);
        this.queryResult = queryResult;
    }

}
