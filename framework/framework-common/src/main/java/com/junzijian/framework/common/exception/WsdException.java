package com.junzijian.framework.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liuzhe
 * @date 2019/8/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WsdException extends RuntimeException {

    private String code;

    private String msg;


    public WsdException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public WsdException(Throwable cause) {
        super(cause);
    }
}
