package com.junzijian.framework.model.oauth.ext;

import lombok.Data;

/**
 * @author bebopze
 * @date 2019/4/29
 */
@Data
public class AuthToken {

    /**
     * 访问token就是短令牌，用户身份令牌
     */
    String access_token;
    /**
     * 刷新token
     */
    String refresh_token;
    /**
     * jwt令牌
     */
    String jwt_token;
}
