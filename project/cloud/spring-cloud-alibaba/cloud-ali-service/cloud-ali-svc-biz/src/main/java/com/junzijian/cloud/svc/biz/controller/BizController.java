package com.junzijian.cloud.svc.biz.controller;

import com.junzijian.cloud.framework.model.biz.param.BuyOrderParam;
import com.junzijian.cloud.svc.biz.service.BizService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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


    @ApiOperation(value = "下单", tags = "cloud")
    @PostMapping(value = "/buy", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> buy(@RequestBody @Valid BuyOrderParam param) {
        return ResultBean.ofSuccess(bizService.buy(param));
    }

    @ApiOperation(value = "下单", tags = "cloud-dubbo")
    @PostMapping(value = "/buy/cloud-dubbo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> buyCloudDubbo(@RequestBody @Valid BuyOrderParam param) {
        return ResultBean.ofSuccess(bizService.buyCloudDubbo(param));
    }
}
