package com.junzijian.cloud.svc.user.controller;

import com.alibaba.fastjson.JSON;
import com.junzijian.framework.common.model.response.ResultBean;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * validator
 *
 * @author liuzhe
 * @date 2019/9/16
 */
@Validated // GET
@RestController
@RequestMapping("/v1/validator")
public class ValidatorController {


    @GetMapping("/get")
    public ResultBean<String> validatorGet(@NotEmpty(message = "name不能为空")
                                           @RequestParam(required = false) String name,

                                           @Min(value = 1, message = "age最小只能1")
                                           @Max(value = 100, message = "age最大只能100")
                                           @RequestParam int age) {

        return ResultBean.ofSuccess("GET==========>" + name + "   " + age);
    }


    /**
     * - @Valid 和 @Validated 没啥大的区别
     * -
     * - @Valid     :    标准JSR-303规范
     * - @Validated :    Spring's JSR-303规范，是标准JSR-303的一个变种
     * -
     * -
     * - 主要区别：  @Valid 可以作用在：成员变量(字段)上，支持：嵌套校验！
     *
     * @param param
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean<String> validatorPost(@Validated @RequestBody ValidParam param) {

        return ResultBean.ofSuccess("POST==========>" + JSON.toJSONString(param));
    }
}


@Data
class ValidParam {

    @Min(value = 1, message = "最小只能1")
    @Max(value = 100, message = "最大只能100")
    private int age;

    @NotBlank(message = "不能为空")
    private String name;

    //    @Validated    // ----- @Validated not applicable to field
    @Valid // ------------------ @Valid可以作用在字段上，嵌套验证就只能用@Valid了
    @NotNull
    @Size(min = 1, message = "至少要有一个属性")
    private List<Prop> propList;
}

@Data
class Prop {

    @NotNull
    @Min(value = 1, message = "必须为正整数")
    private Long pid;

    @NotNull
    @Min(value = 1, message = "必须为正整数")
    private Long vid;

    @NotBlank
    private String pidName;

    @NotBlank
    private String vidName;
}