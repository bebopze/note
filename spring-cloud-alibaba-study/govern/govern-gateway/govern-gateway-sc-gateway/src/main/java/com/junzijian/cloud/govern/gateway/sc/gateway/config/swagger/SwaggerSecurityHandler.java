package com.junzijian.cloud.govern.gateway.sc.gateway.config.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.Optional;

/**
 * @author liuzhe
 * @date 2018/12/17
 */
@Component
public class SwaggerSecurityHandler implements HandlerFunction {

    @Autowired(required = false)
    private SecurityConfiguration securityConfiguration;


    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {

        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(
                        Optional.ofNullable(securityConfiguration)
                                .orElse(SecurityConfigurationBuilder.builder().scopeSeparator(",").build())));
    }

}
