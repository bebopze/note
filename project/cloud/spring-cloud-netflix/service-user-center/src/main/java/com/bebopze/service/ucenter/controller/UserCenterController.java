package com.bebopze.service.ucenter.controller;

import com.bebopze.framework.common.model.response.template.ResultBean;
import com.bebopze.framework.model.ucenter.entity.UserDO;
import com.bebopze.service.ucenter.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bebopze
 * @date 2019/5/5
 */
@RestController
@RequestMapping("/user/center")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;


    @GetMapping("/test")
    public ResultBean test() {
        System.out.println("test---------------");
        return ResultBean.ofSuccess();
    }

    @GetMapping("/getUserByUsername")
    public UserDO getUserByUsername(@RequestParam String username) {
        return userCenterService.getUserByUsername(username);
    }

}
