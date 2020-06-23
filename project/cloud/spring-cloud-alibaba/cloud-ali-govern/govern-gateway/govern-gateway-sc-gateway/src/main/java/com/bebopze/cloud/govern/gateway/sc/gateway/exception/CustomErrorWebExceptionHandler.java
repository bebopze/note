package com.bebopze.cloud.govern.gateway.sc.gateway.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class CustomErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

    @Autowired
    private GatewayExceptionHandlerAdvice gatewayExceptionHandlerAdvice;

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                          ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Map<String, Object> error = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        int errorStatus = getHttpStatus(error);
        Throwable throwable = getError(request);
        return ServerResponse.status(errorStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(gatewayExceptionHandlerAdvice.handle(throwable)))
//                .doOnNext(resp -> logError(request, errorStatus));
                .doOnNext((ServerResponse resp) -> logError(request, resp, throwable));
    }


    // ------------------ DefaultErrorWebExceptionHandler 2.1.2后 方法已删除 -------------------

    private void logError(ServerRequest request, HttpStatus errorStatus) {
        Throwable ex = getError(request);
        if (log.isDebugEnabled()) {
            log.debug(request.exchange().getLogPrefix() + formatError(ex, request));
        }
    }


    // ------------------ copy from AbstractErrorWebExceptionHandler -------------------

//    @Override
//    protected void logError(ServerRequest request, ServerResponse response, Throwable throwable) {
//
//        if (log.isDebugEnabled()) {
//            log.debug(
//                    request.exchange().getLogPrefix() + formatError(throwable, request));
//        }
//        if (response.statusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
//            log.error(request.exchange().getLogPrefix() + "500 Server Error for "
//                    + formatRequest(request), throwable);
//        }
//    }

    private String formatError(Throwable ex, ServerRequest request) {
        String reason = ex.getClass().getSimpleName() + ": " + ex.getMessage();
        return "Resolved [" + reason + "] for HTTP " + request.methodName() + " "
                + request.path();
    }

    private String formatRequest(ServerRequest request) {
        String rawQuery = request.uri().getRawQuery();
        String query = StringUtils.hasText(rawQuery) ? "?" + rawQuery : "";
        return "HTTP " + request.methodName() + " \"" + request.path() + query + "\"";
    }

}
