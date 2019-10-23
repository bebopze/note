package com.junzijian.framework.model.oauth.ext;

import lombok.Data;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
@Data
public class UserOAuthToken {

    /**
     * 访问token就是短令牌，用户身份令牌
     */
    String accessToken;
    /**
     * 刷新token
     */
    String refreshToken;
    /**
     * jwt令牌
     */
    String jwtToken;
}
