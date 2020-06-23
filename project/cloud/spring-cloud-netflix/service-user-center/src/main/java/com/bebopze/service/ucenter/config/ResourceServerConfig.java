package com.bebopze.service.ucenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author bebopze
 * @date 2019-05-05 10:43:21
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 激活方法上的PreAuthorize注解
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 公钥
     */
    private static final String PUBLIC_KEY = "publickey.txt";


    /**
     * 定义JwtTokenStore，使用jwt令牌
     *
     * @param jwtAccessTokenConverter
     * @return
     */
    @Bean
    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    /**
     * 定义JJwtAccessTokenConverter，使用jwt令牌
     *
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setVerifierKey(getPubKey());
        return converter;
    }

    /**
     * Http安全配置，对每个到达系统的http请求链接进行校验
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        // 所有请求必须认证通过
        http.authorizeRequests()
                .antMatchers("/**")
                // 下边的路径放行
//                .antMatchers("//user/center/v2/api-docs", "/swagger-resources/configuration/ui",
//                        "/swagger-resources", "/swagger-resources/configuration/security",
//                        "/swagger-ui.html", "/webjars/**"
//                        , "/user/center/getUserByUsername")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    /**
     * 获取非对称加密公钥 Key
     *
     * @return 公钥 Key
     */
    private String getPubKey() {

        Resource resource = new ClassPathResource(PUBLIC_KEY);

        try {

            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);

            return br.lines().collect(Collectors.joining("\n"));

        } catch (IOException ioe) {
            return null;
        }
    }
}
