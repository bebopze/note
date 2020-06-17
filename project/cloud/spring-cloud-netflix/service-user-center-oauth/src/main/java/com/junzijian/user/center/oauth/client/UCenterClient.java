package com.junzijian.user.center.oauth.client;

import com.junzijian.framework.common.client.ServiceList;
import com.junzijian.framework.model.ucenter.entity.UserDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author bebop
 * @date 2019/5/5
 */
@FeignClient(value = ServiceList.SERVICE_USER_CENTER)
public interface UCenterClient {

    @GetMapping("/user/center/getUserByUsername")
    UserDO getUserByUsername(@RequestParam("username") String username);
}
