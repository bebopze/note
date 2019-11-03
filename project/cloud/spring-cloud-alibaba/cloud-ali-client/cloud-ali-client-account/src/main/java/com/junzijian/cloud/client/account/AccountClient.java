package com.junzijian.cloud.client.account;

import com.junzijian.cloud.framework.common.constant.ServiceConst;
import com.junzijian.cloud.framework.model.account.param.AccountParam;
import com.junzijian.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.ACCOUNT_SERVICE, path = "/v1/account", url = "")
public interface AccountClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Void> save(@RequestBody AccountParam param);

    @GetMapping("/amount/decr")
    ResultBean<Void> decrAmount(@RequestParam("userId") Long userId,
                                @RequestParam("decrAmount") BigDecimal decrAmount);

    @GetMapping("/amount/incr")
    ResultBean<Void> incrAmount(@RequestParam("userId") Long userId,
                                @RequestParam("incrAmount") BigDecimal incrAmount);
}
