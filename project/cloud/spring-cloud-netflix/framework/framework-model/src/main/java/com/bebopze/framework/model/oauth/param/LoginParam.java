package com.bebopze.framework.model.oauth.param;

import lombok.Data;

/**
 * @author bebopze
 * @date 2019/4/29
 */
@Data
public class LoginParam {

    private String username;

    private transient String password;
}
