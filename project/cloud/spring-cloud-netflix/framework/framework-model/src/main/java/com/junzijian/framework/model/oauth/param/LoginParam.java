package com.junzijian.framework.model.oauth.param;

import lombok.Data;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
@Data
public class LoginParam {

    private String username;

    private transient String password;
}
