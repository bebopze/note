package com.junzijian.cloud.client.biz;

import com.junzijian.cloud.framework.common.constant.ServiceConst;
import com.junzijian.cloud.framework.model.biz.param.PlaceOrderParam;
import com.junzijian.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.BIZ_SERVICE, path = "/v1/biz", url = "")
public interface BizClient {

    @PostMapping(value = "/placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> placeOrder(@RequestBody @Validated PlaceOrderParam param);
}
