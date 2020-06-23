package com.bebopze.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * HelloWorld {@link Controller}
 *
 * @author bebopze
 * @since 2018/5/20
 */
@Controller
public class HelloWorldController {

    @RequestMapping("")
    public String index(@RequestParam int value, Model model) {
//        model.addAttribute("acceptLanguage",acceptLanguage);
//        model.addAttribute("jsessionId",jsessionId);
//        model.addAttribute("message","Hello,World");
        return "index";
    }

}
