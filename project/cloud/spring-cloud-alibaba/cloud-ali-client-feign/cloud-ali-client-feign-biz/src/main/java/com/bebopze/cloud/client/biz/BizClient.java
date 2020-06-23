package com.bebopze.cloud.client.biz;

import com.bebopze.cloud.framework.common.constant.ServiceConst;
import com.bebopze.cloud.framework.model.biz.param.BuyOrderParam;
import com.bebopze.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author bebopze
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.BIZ_SERVICE, path = "/v1/biz", url = "")
public interface BizClient {

    @PostMapping(value = "/placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> placeOrder(@RequestBody BuyOrderParam param);
}
