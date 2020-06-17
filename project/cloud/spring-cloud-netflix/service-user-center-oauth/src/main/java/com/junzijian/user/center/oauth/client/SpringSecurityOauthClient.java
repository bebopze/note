package com.junzijian.user.center.oauth.client;

import com.junzijian.framework.common.client.ServiceList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Map;

/**
 * @author bebopze
 * @date 2019/5/11
 */
@FeignClient(ServiceList.SERVICE_USER_CENTER_OAUTH)
public interface SpringSecurityOauthClient {

    @RequestMapping(value = "/oauth/token", method = RequestMethod.POST)
    ResponseEntity<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters);
}
