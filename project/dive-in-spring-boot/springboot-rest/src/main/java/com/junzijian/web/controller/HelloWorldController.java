package com.junzijian.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HelloWorld {@link Controller}
 *
 * @author junzijian
 * @since 2018/5/28
 */
@Controller
public class HelloWorldController {

    @RequestMapping("")
    public String index() {
        return "index";
    }


}
