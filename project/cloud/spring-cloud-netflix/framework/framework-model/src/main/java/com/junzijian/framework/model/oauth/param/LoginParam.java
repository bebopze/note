package com.junzijian.framework.model.oauth.param;

import lombok.Data;

/**
 * @author bebop
 * @date 2019/4/29
 */
@Data
public class LoginParam {

    private String username;

    private transient String password;
}
