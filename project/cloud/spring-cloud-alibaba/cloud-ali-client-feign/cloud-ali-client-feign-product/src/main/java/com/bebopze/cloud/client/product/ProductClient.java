package com.bebopze.cloud.client.product;

import com.bebopze.cloud.framework.common.constant.ServiceConst;
import com.bebopze.cloud.framework.model.product.param.ProductParam;
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
@FeignClient(value = ServiceConst.PRODUCT_SERVICE, path = "/v1/product", url = "")
public interface ProductClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> save(@RequestBody ProductParam param);

    @GetMapping("/detail")
    ResultBean<Object> detail(@RequestParam Long id);
}
