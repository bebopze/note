package com.bebopze.cloud.client.user;

import com.bebopze.cloud.framework.common.constant.ServiceConst;
import com.bebopze.cloud.framework.model.user.param.UserParam;
import com.bebopze.framework.common.model.response.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @author bebopze
 * @date 2019/10/28
 */
@FeignClient(value = ServiceConst.USER_SERVICE, path = "/v1/user", url = "")
public interface UserClient {

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResultBean<Long> save(@RequestBody @Valid UserParam param);
}
