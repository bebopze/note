package com.bebopze.user.center.oauth.service;

import com.bebopze.framework.model.oauth.param.LoginParam;

/**
 * @author bebopze
 * @date 2019/4/29
 */
public interface AuthService {

    String login(LoginParam loginParam);

    void logout();

    String getJwt();
}
