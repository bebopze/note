package com.junzijian.cloud.svc.user.controller;

import com.junzijian.cloud.framework.model.user.param.UserParam;
import com.junzijian.cloud.svc.user.service.UserService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Api(tags = "用户")
@Validated
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "保存用户", notes = "创建/修改")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> save(@RequestBody @Valid UserParam param) {
        return ResultBean.ofSuccess(userService.save(param));
    }
}
