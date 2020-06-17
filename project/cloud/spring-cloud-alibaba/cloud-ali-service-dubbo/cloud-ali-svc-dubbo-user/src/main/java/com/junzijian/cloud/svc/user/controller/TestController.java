package com.junzijian.cloud.svc.user.controller;

import com.google.common.collect.Maps;
import com.junzijian.framework.common.model.response.ResultBean;
import com.junzijian.cloud.svc.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * test
 *
 * @author bebopze
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


    @GetMapping("/jackson")
    public ResultBean<Map> jackson() {

        Date date = new Date();

        Long longNum = 1101241665266728969L;

        // double 无效
        Double doubleNum = 12345678910111213.1234567890;

        // BigDecimal 必须要传入String才能生效
        BigDecimal bigDecimal = new BigDecimal("1234567891011121314151617181920.1234567890");
        // 这样过长 依然会丢失精度
        BigDecimal bigDecimal2 = BigDecimal.valueOf(1234567891011121314151617181920.1234567890);


        HashMap<String, Object> data = Maps.newHashMap();
        data.put("date", date);
        data.put("long", longNum);
        data.put("double", doubleNum);
        data.put("bigDecimal", bigDecimal);
        data.put("bigDecimal2", bigDecimal2);

        return ResultBean.ofSuccess(data);
    }
}