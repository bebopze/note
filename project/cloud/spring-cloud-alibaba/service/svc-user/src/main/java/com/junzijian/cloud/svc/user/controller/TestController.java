package com.junzijian.cloud.svc.user.controller;

import com.junzijian.framework.common.model.response.ResultBean;
import com.junzijian.cloud.svc.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;

/**
 * test
 *
 * @author liuzhe
 * @date 2019/10/28
 */
@Validated
@RestController
@RequestMapping("/v1/test")
public class TestController {

    @Autowired
    private TestService testService;


    @GetMapping("/sayHello")
    public ResultBean<String> sayHello(@RequestParam @NotEmpty(message = "name不能为空") String name,
                                       @RequestParam @NotEmpty(message = "name2不能为空") String name2) {

        return ResultBean.ofSuccess(testService.sayHello(name, name2));
    }
}