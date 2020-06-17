package com.junzijian.cloud.govern.gateway.sc.gateway.service.impl;

import com.junzijian.cloud.govern.gateway.sc.gateway.service.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 动态路由实现
 *
 * @author bebopze
 * @date 2019/6/23
 */
@Service
public class DynamicRouteServiceImpl implements DynamicRouteService {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

    }

}
