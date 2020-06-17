package com.junzijian.cloud.govern.gateway.sc.gateway.service;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author bebopze
 * @date 2019/6/23
 */
@Data
@Component
public class DynamicRouteServiceByNacos {

    private String dataId;
    private String group;


    @Autowired
    private DynamicRouteService dynamicRouteService;


    public DynamicRouteServiceByNacos() {

        new DynamicRouteServiceByNacos("gateway-route", "gateway-group");
    }


    public DynamicRouteServiceByNacos(String dataId, String group) {
        this.dataId = dataId;
        this.group = group;
    }


    public static void main(String[] args) throws NacosException, InterruptedException {

        String serverAddr = "localhost";
        String dataId = "test_1";
        String group = "DEFAULT_GROUP";

        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);

        configService.addListener(dataId, group, new Listener() {

            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("recieve:" + configInfo);
            }

            @Override
            public Executor getExecutor() {
                return null;
            }
        });

        boolean isPublishOk = configService.publishConfig(dataId, group, "content");
        System.out.println(isPublishOk);

        Thread.sleep(3000);
        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);

        boolean isRemoveOk = configService.removeConfig(dataId, group);
        System.out.println(isRemoveOk);
        Thread.sleep(3000);

        content = configService.getConfig(dataId, group, 5000);
        System.out.println(content);
        Thread.sleep(300000);

    }
}
