package com.junzijian.user.center.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.junzijian.framework.common.client.ServiceList;
import com.junzijian.framework.common.exception.ExceptionCast;
import com.junzijian.framework.common.model.response.AuthCode;
import com.junzijian.framework.model.oauth.ext.AuthToken;
import com.junzijian.framework.model.oauth.ext.UserOAuthToken;
import com.junzijian.framework.model.oauth.param.LoginParam;
import com.junzijian.user.center.oauth.client.SpringSecurityOauthClient;
import com.junzijian.user.center.oauth.service.AuthService;
import com.junzijian.user.center.oauth.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.junzijian.framework.common.constant.OAuthConst.COOKIE_KEY;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.cookieDomain}")
    private String cookieDomain;
    @Value("${auth.cookieMaxAge}")
    private int cookieMaxAge;
    @Value("${auth.tokenValiditySeconds}")
    private int tokenValiditySeconds;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private SpringSecurityOauthClient springSecurityOauthClient;


    @Override
    public String login(LoginParam loginParam) {
        if (null == loginParam || StringUtils.isEmpty(loginParam.getUsername())) {
            ExceptionCast.cast(AuthCode.AUTH_USERNAME_NONE);
        }
        if (null == loginParam || StringUtils.isEmpty(loginParam.getPassword())) {
            ExceptionCast.cast(AuthCode.AUTH_PASSWORD_NONE);
        }

        // 账号
        String username = loginParam.getUsername();
        // 密码
        String password = loginParam.getPassword();

        // 申请令牌
        AuthToken authToken = login(username, password, clientId, clientSecret);

        // 用户身份令牌
        String access_token = authToken.getAccess_token();

        // 将令牌存储到cookie
        this.saveCookie(access_token);

        return access_token;
    }


    @Override
    public String getJwt() {

        // 取出cookie中的用户身份令牌
        String uid = getTokenFormCookie();
        Assert.notNull(uid, "用户不存在");

        // 拿身份令牌从redis中查询jwt令牌
        UserOAuthToken userToken = getUserToken(uid);
        if (userToken != null) {
            // 将jwt令牌返回给用户
            String jwt_token = userToken.getJwtToken();
            return jwt_token;
        }

        return null;
    }

    @Override
    public void logout() {

        // 取出cookie中的用户身份令牌
        String uid = getTokenFormCookie();
        // 删除redis中的token
        boolean result = delToken(uid);
        // 清除cookie
        clearCookie(uid);
    }


    /**
     * login
     *
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken login(String username, String password, String clientId, String clientSecret) {

        // 请求spring security申请令牌
        AuthToken authToken = applyToken(username, password, clientId, clientSecret);
        if (authToken == null) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_APPLYTOKEN_FAIL);
        }

        // 用户身份令牌
        String access_token = authToken.getAccess_token();

        // 存储到redis中的内容
        String jsonString = JSON.toJSONString(authToken);

        // 将令牌存储到redis
        boolean result = this.saveToken(access_token, jsonString, tokenValiditySeconds);
        if (!result) {
            ExceptionCast.cast(AuthCode.AUTH_LOGIN_TOKEN_SAVEFAIL);
        }

        return authToken;
    }

    /**
     * 申请令牌
     *
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken applyToken(String username, String password, String clientId, String clientSecret) {
        // 从eureka中获取认证服务的地址（因为spring security在认证服务中）
        // 从eureka中获取认证服务的一个实例的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceList.SERVICE_USER_CENTER_OAUTH);
        // 此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        // 令牌申请的地址 http://localhost:2201/oauth/token
        String authUrl = uri + "/oauth/token";
        // 定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);

        // 定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        // String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables

        // 设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        // 申请令牌信息
        Map bodyMap = exchange.getBody();
        if (bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null) {

            // 解析spring security返回的错误信息
            if (bodyMap != null && bodyMap.get("error_description") != null) {
                String error_description = (String) bodyMap.get("error_description");
                if (error_description.indexOf("UserDetailsService returned null") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                } else if (error_description.indexOf("坏的凭证") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }

            return null;
        }

        AuthToken authToken = new AuthToken();
        // 用户身份令牌
        authToken.setAccess_token((String) bodyMap.get("jti"));
        // 刷新令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));
        // jwt令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));

        return authToken;
    }

    /**
     * 申请令牌
     *
     * @param username
     * @param password
     * @param clientId
     * @param clientSecret
     * @return
     */
    private AuthToken applyToken2(String username, String password, String clientId, String clientSecret) {

        // 从eureka中获取认证服务的地址（因为spring security在认证服务中）
        // 从eureka中获取认证服务的一个实例的地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceList.SERVICE_USER_CENTER_OAUTH);
        // 此地址就是http://ip:port
        URI uri = serviceInstance.getUri();
        // 令牌申请的地址 http://localhost:2201/oauth/token
        String authUrl = uri + "/oauth/token";
        // 定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        String httpBasic = getHttpBasic(clientId, clientSecret);
        header.add("Authorization", httpBasic);

        // 定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        // String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables

        // 设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });

        ResponseEntity<Map> exchange = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);

        // 申请令牌信息
        Map bodyMap = exchange.getBody();
        if (bodyMap == null ||
                bodyMap.get("access_token") == null ||
                bodyMap.get("refresh_token") == null ||
                bodyMap.get("jti") == null) {

            // 解析spring security返回的错误信息
            if (bodyMap != null && bodyMap.get("error_description") != null) {
                String error_description = (String) bodyMap.get("error_description");
                if (error_description.indexOf("UserDetailsService returned null") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_ACCOUNT_NOTEXISTS);
                } else if (error_description.indexOf("坏的凭证") >= 0) {
                    ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
                }
            }

            return null;
        }

        AuthToken authToken = new AuthToken();
        // 用户身份令牌
        authToken.setAccess_token((String) bodyMap.get("jti"));
        // 刷新令牌
        authToken.setRefresh_token((String) bodyMap.get("refresh_token"));
        // jwt令牌
        authToken.setJwt_token((String) bodyMap.get("access_token"));

        return authToken;
    }

    /**
     * 获取httpbasic的串
     *
     * @param clientId
     * @param clientSecret
     * @return
     */
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        // 将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }

    /**
     * @param access_token 用户身份令牌
     * @param content      内容就是AuthToken对象的内容
     * @param ttl          过期时间
     * @return
     */
    private boolean saveToken(String access_token, String content, long ttl) {
        String key = "user_token:" + access_token;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
        return expire > 0;
    }

    /**
     * 将令牌存储到cookie
     *
     * @param token
     */
    private void saveCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response,
                cookieDomain, "/", COOKIE_KEY,
                token, cookieMaxAge, false);
    }

    /**
     * 取出cookie中的身份令牌
     *
     * @return
     */
    private String getTokenFormCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, COOKIE_KEY);
        if (map != null && map.get(COOKIE_KEY) != null) {
            String uid = map.get(COOKIE_KEY);
            return uid;
        }
        return null;
    }

    /**
     * 删除token
     *
     * @param access_token
     * @return
     */
    private boolean delToken(String access_token) {
        String key = "user_token:" + access_token;
        stringRedisTemplate.delete(key);
        return true;
    }

    /**
     * 从cookie删除token
     *
     * @param token
     */
    private void clearCookie(String token) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        CookieUtil.addCookie(response, cookieDomain, "/", COOKIE_KEY, token, 0, false);
    }

    /**
     * 从redis查询令牌
     *
     * @param token
     * @return
     */
    public UserOAuthToken getUserToken(String token) {
        String key = "user_token:" + token;
        // 从redis中取到令牌信息
        String value = stringRedisTemplate.opsForValue().get(key);
        // 转成对象
        UserOAuthToken authToken = JSON.parseObject(value, UserOAuthToken.class);
        return authToken;
    }
}
