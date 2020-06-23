package com.bebopze.cloud.client.order.dubbo;

import com.bebopze.cloud.framework.common.constant.ServiceConst;
import com.bebopze.cloud.framework.model.order.entity.OrderDO;
import com.bebopze.cloud.framework.model.order.param.OrderParam;
import com.bebopze.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bebopze
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.ORDER_SERVICE, path = "/v1/order", url = "")
public interface OrderClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> save(@RequestBody OrderParam param);

    @GetMapping("/detail")
    ResultBean<OrderDO> detail(@RequestParam("orderId") Long orderId);
}
