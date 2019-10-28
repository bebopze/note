package com.junzijian.cloud.client.order;

import com.junzijian.cloud.framework.common.constant.ServiceConst;
import com.junzijian.cloud.framework.model.order.param.OrderParam;
import com.junzijian.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.ORDER_SERVICE, path = "/v1/order", url = "")
public interface OrderClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> save(@RequestBody OrderParam param);

    @GetMapping("/detail")
    ResultBean<Object> detail(@RequestParam Long orderId);
}
