package com.junzijian.framework.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.junzijian.framework.common.exception.ExceptionCast;
import com.junzijian.framework.common.model.CustomContext;
import com.junzijian.framework.common.model.CustomContextVO;
import com.junzijian.framework.common.model.response.code.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 自定义Interceptor
 *
 * @author bebopze
 * @date 2019/10/24
 */
@Slf4j
@Component
public class CustomInterceptor implements HandlerInterceptor {

    /**
     * 请求时间戳最大间隔：5s
     */
    private static final int request_timestamp_max_interval = 5;

    private static final String HEADER_ACCESS_TOKEN = "accessToken";
    private static final String HEADER_REQUEST_TIMESTAMP = "reqTimestamp";
    private static final String HEADER_PARTNER_ID = "partnerId";
    private static final String HEADER_SALESMAN_ID = "salesmanId";
    private static final String HEADER_ORDER_ID = "orderId";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logHeader(request);

//        // 验证通用参数
//        checkCommonParam(request);
//
//        // 验证请求是时间
//        checkReqTimestamp(request);
//
//        // 验证api权限
//        checkApiPermission(request);
//
//        // 验证订单权限
//        checkOrderPermission(request);

        // context
        addOpenApiContext(request);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        CustomContext.remove();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }


    /**
     * log header
     *
     * @param request
     */
    private void logHeader(HttpServletRequest request) {

        Map<String, String> headerMap = Maps.newHashMap();

        headerMap.put(HEADER_ACCESS_TOKEN, request.getHeader(HEADER_ACCESS_TOKEN));
        headerMap.put(HEADER_REQUEST_TIMESTAMP, request.getHeader(HEADER_REQUEST_TIMESTAMP));
        headerMap.put(HEADER_PARTNER_ID, request.getHeader(HEADER_PARTNER_ID));
        headerMap.put(HEADER_SALESMAN_ID, request.getHeader(HEADER_SALESMAN_ID));
        headerMap.put(HEADER_ORDER_ID, request.getHeader(HEADER_ORDER_ID));

        log.info("{} {}",
                request.getContextPath() + request.getServletPath(),
                JSON.toJSONString(headerMap));
    }

    /**
     * check common param
     *
     * @param request
     */
    private void checkCommonParam(HttpServletRequest request) {

        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String reqTimestamp = request.getHeader(HEADER_REQUEST_TIMESTAMP);
        String partnerId = request.getHeader(HEADER_PARTNER_ID);
        String salesmanId = request.getHeader(HEADER_SALESMAN_ID);
        String orderId = request.getHeader(HEADER_ORDER_ID);

        Assert.hasText(accessToken, "公共参数有误：accessToken不能为空");
        Assert.hasText(reqTimestamp, "公共参数有误：reqTimestamp不能为空");
        Assert.hasText(partnerId, "公共参数有误：partnerId不能为空");
        Assert.hasText(salesmanId, "公共参数有误：salesmanId不能为空");
        Assert.hasText(orderId, "公共参数有误：orderId不能为空");
    }

    /**
     * check request timestamp
     *
     * @param request
     */
    private void checkReqTimestamp(HttpServletRequest request) {

        String timestamp = request.getHeader(HEADER_REQUEST_TIMESTAMP);
        Integer requestTimestamp = Integer.valueOf(timestamp);

        long nowTimestamp = System.currentTimeMillis() / 1000;

        if (nowTimestamp - requestTimestamp > request_timestamp_max_interval) {
//            throw new CustomException(CommonCode.REQ_TIMEOUT);
            ExceptionCast.cast(CommonCode.REQ_TIMEOUT);
        }
    }

    /**
     * check api permission
     *
     * @param request
     */
    private void checkApiPermission(HttpServletRequest request) {

        String requestPath = request.getContextPath() + request.getServletPath();

        // TODO ...


    }

    /**
     * check order permission
     *
     * @param request
     */
    private void checkOrderPermission(HttpServletRequest request) throws Exception {

        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN);
        String partnerId = request.getHeader(HEADER_PARTNER_ID);
        String salesmanId = request.getHeader(HEADER_SALESMAN_ID);
        String orderId = request.getHeader(HEADER_ORDER_ID);


        // orderId -> partnerId / salesmanId


        // salesmanId -> partnerId


        // partnerId -> accessToken

    }

    /**
     * add CustomContext
     *
     * @param request
     */
    private void addOpenApiContext(HttpServletRequest request) {

        CustomContextVO customContextVO = new CustomContextVO();

        // common
        customContextVO.setAccessToken(request.getHeader(HEADER_ACCESS_TOKEN));
        customContextVO.setPartnerId(request.getHeader(HEADER_PARTNER_ID));
        customContextVO.setSalesmanId(request.getHeader(HEADER_SALESMAN_ID));
        customContextVO.setOrderId(request.getHeader(HEADER_ORDER_ID));

        CustomContext.add(customContextVO);
    }
}