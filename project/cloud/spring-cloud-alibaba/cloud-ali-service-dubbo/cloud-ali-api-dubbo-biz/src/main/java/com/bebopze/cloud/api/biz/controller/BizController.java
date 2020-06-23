package com.bebopze.cloud.api.biz.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.bebopze.cloud.api.biz.service.BizService;
import com.bebopze.cloud.framework.model.biz.param.BuyOrderParam;
import com.bebopze.framework.common.model.response.ResultBean;
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
 * @author bebopze
 * @date 2019/10/28
 */
@Api(tags = "业务")
@Validated
@RestController
@RequestMapping("/v1/biz")
public class BizController {

    @Autowired
    private BizService bizService;


    /**
     * - @SentinelResource 注解用来标识资源是否被限流、降级。本例该注解的属性 hello 表示资源名。
     * -
     * - @SentinelResource 还提供了其它额外的属性如 blockHandler，blockHandlerClass，fallback，
     * - 用于表示限流或降级的操作（注意有方法签名要求）。
     * -
     * -
     * - 一般推荐将 @SentinelResource 注解加到服务上！！！
     * -
     * - 在 Web 层直接使用 Spring Cloud Alibaba 自带的 Web 适配埋点。
     *
     * @param param
     * @return
     */
    @SentinelResource("hello")
    @ApiOperation(value = "下单", tags = "cloud-dubbo")
    @PostMapping(value = "/buy/cloud-dubbo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> buy(@RequestBody @Valid BuyOrderParam param) {
        return ResultBean.ofSuccess(bizService.buy(param));
    }
}
