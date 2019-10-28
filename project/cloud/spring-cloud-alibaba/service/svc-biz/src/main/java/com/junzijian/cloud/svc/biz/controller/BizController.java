package com.junzijian.cloud.svc.biz.controller;

import com.junzijian.cloud.framework.model.biz.param.PlaceOrderParam;
import com.junzijian.cloud.svc.biz.service.BizService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 业务
 *
 * @author junzijian
 * @date 2019/10/28
 */
@Api(tags = "业务")
@Validated
@RestController
@RequestMapping("/v1/biz")
public class BizController {

    @Autowired
    private BizService bizService;


    @ApiOperation("下单")
    @PostMapping(value = "/placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> placeOrder(@RequestBody @Validated PlaceOrderParam param) {
        return ResultBean.ofSuccess(bizService.placeOrder(param));
    }
}
