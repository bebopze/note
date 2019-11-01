package com.junzijian.cloud.svc.storage.controller;

import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.cloud.svc.storage.service.StorageService;
import com.junzijian.framework.common.model.response.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@Api(tags = "库存")
@Validated
@RestController
@RequestMapping("/v1/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;


    @ApiOperation(value = "保存库存", notes = "创建/修改")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResultBean<Long> save(@RequestBody @Valid StorageParam param) {
        return ResultBean.ofSuccess(storageService.save(param));
    }
}
