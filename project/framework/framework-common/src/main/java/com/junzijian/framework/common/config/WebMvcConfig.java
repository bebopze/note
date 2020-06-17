package com.junzijian.framework.common.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.collect.Lists;
import com.junzijian.framework.common.interceptor.CustomInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bebopze
 * @date 2019/10/24
 */
@Configuration
/**
 * tips: 不要在网上随便copy 增加 @EnableWebMvc 注解, 它会覆盖 SpringBoot MVC 的默认配置❗️❗️❗️
 *
 * 1. extends WebMvcConfigurationSupport：
 *
 *      在添加拦截器并继承 WebMvcConfigurationSupport 后
 *      会覆盖 @EnableAutoConfiguration 关于WebMvcAutoConfiguration的配置！
 *
 *      从而导致所有的 spring.jackson.xx 配置失效！！！
 *
 *
 *  2.  implements WebMvcConfigurer + @EnableWebMvc
 *
 *      效果&副作用 同上！！！
 *
 * @see <a href="https://www.52jingya.com/aid13381.html"/>
 *
 */
//@EnableWebMvc ❌❌❌
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private CustomInterceptor customInterceptor;


    /**
     * 跨域支持
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("OPTIONS", "GET", "POST", "DELETE", "PUT")
                .maxAge(1800);
    }

    /**
     * add interceptors
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // exclude
        List<String> excludePathPatterns = Lists.newArrayList(
                "/error",
                "/v1/common/**"
        );
        // exclude swagger
        excludeSwagger(excludePathPatterns);

        // add interceptor
        registry.addInterceptor(customInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePathPatterns);
    }

    /**
     * 添加静态资源 -- swagger-ui
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * exclude swagger
     *
     * @param excludePathPatterns
     * @return
     */
    private void excludeSwagger(List<String> excludePathPatterns) {

        // 放开swagger文档
        excludePathPatterns.addAll(Lists.newArrayList(
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**")
        );
    }


//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//
//        converters.add(number2StringConverter());
//    }
//
//    /**
//     * number -> String Converter
//     * <p>
//     * TODO 此种解决方案 和 spring.jackson.date-format=yyyy-MM-dd HH:mm:ss 冲突！！！
//     * <p>
//     * TODO 最终解决方案：spring.jackson.generator.write-numbers-as-strings=true
//     *
//     * @return
//     */
//    private HttpMessageConverter<?> number2StringConverter() {
//
//        SimpleModule simpleModule = new SimpleModule();
//        // 将Long、BigInteger 序列化的时候，转化为String
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//        simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(simpleModule);
//
//        MappingJackson2HttpMessageConverter number2StringConverter = new MappingJackson2HttpMessageConverter();
//        number2StringConverter.setObjectMapper(objectMapper);
//
//        return number2StringConverter;
//    }


//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        removeDefaultConver(converters);
//
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        //自定义配置...
//        FastJsonConfig config = new FastJsonConfig();
//        config.setSerializerFeatures(
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullStringAsEmpty
//        );
//        fastJsonHttpMessageConverter.setFastJsonConfig(config);
//
//        List<MediaType> supportedMediaTypes = new ArrayList<>();
//        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
//        converters.add(fastJsonHttpMessageConverter);
//    }
//
//    private void removeDefaultConver(List<HttpMessageConverter<?>> converters) {
//        converters.removeIf(e -> e instanceof MappingJackson2HttpMessageConverter);
//    }

}
