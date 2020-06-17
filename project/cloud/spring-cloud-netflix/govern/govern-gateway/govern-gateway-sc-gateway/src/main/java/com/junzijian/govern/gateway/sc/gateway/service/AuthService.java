package com.junzijian.govern.gateway.sc.gateway.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.junzijian.framework.common.constant.OAuthConst.COOKIE_KEY;

/**
 * 身份认证
 *
 * @author bebop
 * @date 2018/11/13
 */
@Service
public class AuthService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Value("${gate.ignore.authentication.startWith}")
    private String ignoreUrls;


    /**
     * 从头取出jwt令牌
     *
     * @param request
     * @return
     */
    public String getJwtFromHeader(ServerHttpRequest request) {
        // 取出头信息
        String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isEmpty(authorization)) {
            return null;
        }
        if (!authorization.startsWith("Bearer ")) {
            return null;
        }
        // 取到jwt令牌
        String jwt = authorization.substring(7);

        return jwt;
    }

    /**
     * 从cookie取出token  查询身份令牌
     *
     * @param request
     * @return
     */
    public String getTokenFromCookie(ServerHttpRequest request) {
        Map<String, String> cookieMap = CookieUtil.readCookie(request, COOKIE_KEY);
        String access_token = cookieMap.get(COOKIE_KEY);
        if (StringUtils.isEmpty(access_token)) {
            return null;
        }
        return access_token;
    }

    /**
     * 查询令牌的有效期
     *
     * @param access_token
     * @return
     */
    public long getExpire(String access_token) {
        // key
        String key = "user_token:" + access_token;
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire;
    }

    /**
     * 忽略url校验
     *
     * @param url
     * @return
     */
    public boolean ignoreAuthentication(String url) {
        return Stream.of(this.ignoreUrls.split(",")).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    /**
     * swagger
     *
     * @param url
     * @return
     */
    public boolean isSwaggerUrl(String url) {
        // http://localhost:8090/api/user/center/v2/api-docs
        return url.contains("/v2/api-docs");
    }
}
