package com.bebopze.jdk.spi;

import sun.misc.Service;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SPI                      - https://www.jianshu.com/p/3a3edbcd8f24
 *
 * @author bebopze
 * @date 2020/9/8
 */
public class SPI_Test {


    // 本质：
    //      本地 服务发现


    //  SPI：
    //      全称为 Service Provider Interface，是一种 服务发现机制


    //  实现：
    //      它通过在ClassPath路径下的META-INF/services文件夹查找文件，自动加载文件里所定义的类


    //  应用：
    //      这一机制为很多框架扩展提供了可能，比如在 Dubbo、JDBC 中都使用到了SPI机制


    // --------------------------------------------------------------------------------


    public static void main(String[] args) {

        // 方式1：ServiceLoader.load
        serviceLoader_load();


        System.out.println("-----------------------");


        // 方式2：Service.providers
        service_providers();
    }


    private static void serviceLoader_load() {

        ServiceLoader<SPIService> serviceLoader = ServiceLoader.load(SPIService.class);
        Iterator<SPIService> iterator = serviceLoader.iterator();


        while (iterator.hasNext()) {
            SPIService spiService = iterator.next();
            spiService.execute();
        }
    }

    private static void service_providers() {

        Iterator<SPIService> providers = Service.providers(SPIService.class);

        while (providers.hasNext()) {
            SPIService spiService = providers.next();
            spiService.execute();
        }
    }
}
