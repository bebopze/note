package com.bebopze.cloud.govern.gateway.sc.gateway.service;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bebopze
 * @date 2018/11/13
 */
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期 以秒为单位
     */
//    public static void addCookie(HttpServletResponse response, String domain, String path, String name,
//                                 String value, int maxAge, boolean httpOnly) {
//        Cookie cookie = new Cookie(name, value);
//        cookie.setDomain(domain);
//        cookie.setPath(path);
//        cookie.setMaxAge(maxAge);
//        cookie.setHttpOnly(httpOnly);
//        response.addCookie(cookie);
//    }


    /**
     * 根据cookie名称读取cookie
     *
     * @param request
     * @return map<cookieName,cookieValue>
     */

    public static Map<String, String> readCookie(ServerHttpRequest request, String... cookieNames) {
        Map<String, String> cookieMap = new HashMap<>();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (!CollectionUtils.isEmpty(cookies)) {

            cookies.values().stream()
                    .filter(list -> null != list && list.size() != 0)
                    .forEach(httpCookieList -> {
                        String name = httpCookieList.get(0).getName();
                        String value = httpCookieList.get(0).getValue();
                        cookieMap.put(name, value);
                    });
        }

        return cookieMap;
    }
}
