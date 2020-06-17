package com.junzijian.govern.gateway.sc.gateway.config.swagger;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;
import java.util.Map;

/**
 * @author bebopze
 * @date 2018/12/17
 */
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String API_URI = "/v2/api-docs";

    private final RouteLocator routeLocator;

    private final GatewayProperties gatewayProperties;


    @Override
    public List<SwaggerResource> get() {

        List<SwaggerResource> resources = Lists.newArrayList();
        List<String> routes = Lists.newArrayList();

        // 取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        // 结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
        gatewayProperties.getRoutes().stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition ->
                        routeDefinition.getPredicates().stream()
                                .filter(predicateDefinition -> "Path".equalsIgnoreCase(predicateDefinition.getName()))
                                .forEach(predicateDefinition -> {

                                    // name
                                    String name = routeDefinition.getId();

                                    // location
                                    Map<String, String> args = predicateDefinition.getArgs();
                                    String val = args.get(NameUtils.GENERATED_NAME_PREFIX + "0");
                                    String location = val.replace("/**", API_URI);

                                    // swaggerResource
                                    SwaggerResource swaggerResource = swaggerResource(name, location);

                                    // add
                                    resources.add(swaggerResource);
                                }));

        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}
