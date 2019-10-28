package com.junzijian.cloud.svc.account.controller;

import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.cloud.svc.account.service.AccountService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Api(tags = "账户")
@Validated
@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @ApiOperation(value = "保存账户", notes = "创建/修改")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Void> save(@RequestBody @Validated AccountParam param) {
        accountService.save(param);
        return ResultBean.ofSuccess();
    }
}