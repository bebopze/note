package com.bebopze.jdk.frame.ratelimiter.rule;

import lombok.Data;

/**
 * @author bebopze
 * @date 2020/8/17
 */
@Data
public class ApiLimit {

    /**
     * // 1 second
     */
    private static final int DEFAULT_TIME_UNIT = 1;
    private String api;
    private int limit;
    private int unit = DEFAULT_TIME_UNIT;


    public ApiLimit() {
    }

    public ApiLimit(String api, int limit) {
        this(api, limit, DEFAULT_TIME_UNIT);
    }

    public ApiLimit(String api, int limit, int unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }
}