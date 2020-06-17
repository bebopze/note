package com.junzijian.user.center.oauth.service;

import com.junzijian.framework.model.oauth.param.LoginParam;

/**
 * @author bebop
 * @date 2019/4/29
 */
public interface AuthService {

    String login(LoginParam loginParam);

    void logout();

    String getJwt();
}
