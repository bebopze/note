package com.junzijian.cloud.client.storage;

import com.junzijian.cloud.framework.common.constant.ServiceConst;
import com.junzijian.cloud.framework.model.storage.param.StorageParam;
import com.junzijian.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author junzijian
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.STORAGE_SERVICE, path = "/v1/storage", url = "")
public interface StorageClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> save(@RequestBody StorageParam param);
}
