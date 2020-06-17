package com.junzijian.govern.gateway.sc.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.junzijian.framework.common.model.response.template.ResultBean;
import com.junzijian.govern.gateway.sc.gateway.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 请求url权限校验
 *
 * @author bebopze
 * @date 2018/11/19
 */
@Slf4j
@Component
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthService authService;


    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 1.首先网关检查token是否有效，无效直接返回401，不调用签权服务
     * 2.调用签权服务器看是否对该请求有权限，有权限进入下一个filter，没有权限返回401
     *
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();
        String url = request.getPath().value();
        HttpHeaders headers = request.getHeaders();


        // 可在此处做日志打印！！！ path/param/ip/time  e.g.
        log.info("url : {} , method : {} , authentication : {}, headers : {}",
                url, method, authentication, headers);

        // swagger放行
        if (authService.isSwaggerUrl(url)) {
            return chain.filter(exchange);
        }

        // 不需要网关签权的url --> 放行
        if (authService.ignoreAuthentication(url)) {
            return chain.filter(exchange);
        }

        // check JWT
        if (checkJWT(request)) {
            // 放行
            return chain.filter(exchange);
        }

        // 不通过 拒绝
        return unauthorized(exchange);
    }


    /**
     * 过虑器的内容
     * 测试的需求：过虑所有请求，判断头部信息是否有Authorization，如果没有则拒绝访问，否则转发到微服务。
     *
     * @param request
     * @return
     */
    public boolean checkJWT(ServerHttpRequest request) {

        // 取cookie中的身份令牌
        String token = authService.getTokenFromCookie(request);
        if (StringUtils.isEmpty(token)) {
            //拒绝访问
            return false;
        }
        // 从header中取jwt
        String jwt = authService.getJwtFromHeader(request);
        if (StringUtils.isEmpty(jwt)) {
            // 拒绝访问
            return false;
        }
        // 从redis取出jwt的过期时间
        long expire = authService.getExpire(token);
        if (expire < 0) {
            // 拒绝访问
            return false;
        }

        // 通过
        return true;
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory()
                .wrap(JSON.toJSONBytes(ResultBean.ofError("UNAUTHORIZED")));
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

}
