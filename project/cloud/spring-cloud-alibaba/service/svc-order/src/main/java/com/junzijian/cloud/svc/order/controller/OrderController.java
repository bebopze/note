package com.junzijian.cloud.svc.order.controller;

import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.cloud.svc.order.service.OrderService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Api(tags = "订单")
@Validated
@RestController
@RequestMapping("/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "保存订单", notes = "创建/修改")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> save(@RequestBody @Valid OrderParam param) {
        return ResultBean.ofSuccess(orderService.save(param));
    }

    @GetMapping("/detail")
    public ResultBean<Object> detail(@RequestParam @NotNull(message = "orderId不能为空") Long orderId) {
        return ResultBean.ofSuccess(orderService.detail(orderId));
    }
}
