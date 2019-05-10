package com.junzijian.govern.gateway.sc.gateway.service;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuzhe
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
        Map<String, String> cookieMap = new HashMap<String, String>();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        if (cookies != null) {

//            for (MultiValueMap<String, HttpCookie> cookie : cookies) {
//                String cookieName = cookie.getName();
//                String cookieValue = cookie.getValue();
//                for (int i = 0; i < cookieNames.length; i++) {
//                    if (cookieNames[i].equals(cookieName)) {
//                        cookieMap.put(cookieName, cookieValue);
//                    }
//                }
//            }
        }

        return cookieMap;
    }
}
