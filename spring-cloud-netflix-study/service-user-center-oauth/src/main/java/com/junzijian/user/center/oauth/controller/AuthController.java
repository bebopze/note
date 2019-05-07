package com.junzijian.user.center.oauth.controller;

import com.junzijian.framework.common.model.response.template.ResultBean;
import com.junzijian.framework.model.oauth.param.LoginParam;
import com.junzijian.user.center.oauth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liuzhe
 * @date 2019/4/29
 */
@RestController
@RequestMapping("/user/center/oauth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @ApiOperation("登录")
//    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("login")
//    public ResultBean<String> login(@RequestBody LoginParam loginParam) {
    public ResultBean<String> login(@RequestParam String username,
                                    @RequestParam String password) {

        LoginParam loginParam = new LoginParam();
        loginParam.setUsername(username);
        loginParam.setPassword(password);
        return ResultBean.ofSuccess(authService.login(loginParam));
    }

    @GetMapping("logout")
    @ApiOperation("退出")
    public ResultBean logout() {
        authService.logout();
        return ResultBean.ofSuccess();
    }

    @GetMapping("jwt")
    @ApiOperation("查询用户jwt令牌")
    public ResultBean<String> jwt() {
        return ResultBean.ofSuccess(authService.getJwt());
    }
}
