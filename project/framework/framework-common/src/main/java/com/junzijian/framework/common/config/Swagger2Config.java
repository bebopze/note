package com.junzijian.framework.common.config;

import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author liuzhe
 * @date 2019/10/24
 */
@Configuration
@EnableSwagger2
@ConditionalOnExpression("${swagger-ui.open}")
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        List<Parameter> globalOperationParameters = Lists.newArrayList();
        // common header
        globalOperationParameters.addAll(commonRequestHeader());
        // token header
        globalOperationParameters.addAll(tokenHeader());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.junzijian"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(globalOperationParameters)
                .apiInfo(apiInfo());
    }


    /**
     * common header
     *
     * @return
     */
    private List<Parameter> commonRequestHeader() {

        Parameter accessToken = new ParameterBuilder()
                .name("accessToken")
                .description("访问令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        Parameter reqTimestamp = new ParameterBuilder()
                .name("reqTimestamp")
                .description("请求时间戳(单位：秒)")
                .modelRef(new ModelRef("int"))
                .parameterType("header")
                .required(false)
                .build();

        Parameter partnerId = new ParameterBuilder()
                .name("partnerId")
                .description("合伙人ID")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        Parameter salesmanId = new ParameterBuilder()
                .name("salesmanId")
                .description("业务员ID")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        Parameter orderId = new ParameterBuilder()
                .name("orderId")
                .description("订单号")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return Lists.newArrayList(accessToken, reqTimestamp, partnerId, salesmanId, orderId);
    }

    /**
     * token header
     *
     * @return
     */
    private List<Parameter> tokenHeader() {

        Parameter tokenParToken = new ParameterBuilder()
                .name("token")
                .description("令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        return Lists.newArrayList(tokenParToken);
    }

    /**
     * apiInfo
     *
     * @return
     */
    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("云车金融-OpenApi文档")
                .description("云车金融-OpenApi文档")
                .version("1.0")
                .build();
    }

}