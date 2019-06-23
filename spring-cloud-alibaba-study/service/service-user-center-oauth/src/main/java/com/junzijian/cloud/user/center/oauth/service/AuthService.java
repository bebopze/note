package com.junzijian.cloud.user.center.oauth.service;

import com.junzijian.framework.model.oauth.param.LoginParam;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
public interface AuthService {

    String login(LoginParam loginParam);

    void logout();

    String getJwt();
}
