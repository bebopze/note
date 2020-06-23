package com.bebopze.framework.stream.mq;

import com.alibaba.fastjson.JSON;
import com.bebopze.framework.common.model.response.template.ResultBean;
import com.bebopze.framework.stream.config.MyStreams;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

/**
 * @author bebopze
 * @date 2019/2/26
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/mq/stream")
public class MqStreamController {

    @Autowired
    private MyStreams myStreams;


    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean send(@RequestBody Msg msg) {

        // topic_1
        topic_1(msg);

        // topic_2
        topic_2(msg);

        return ResultBean.ofSuccess();
    }


    private void topic_1(Msg msg) {

        // do logic service
        System.out.println("do logic service ===========> topic_1");

        // send msg
        myStreams.my_output_1()
                .send(MessageBuilder.withPayload(msg)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build()
                );
    }

    private void topic_2(Msg msg) {

        // do logic service
        System.out.println("do logic service ===========> topic_2");

        // send msg
        myStreams.my_output_2()
                .send(MessageBuilder.withPayload(msg)
                        .setHeader(MessageHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .build()
                );
    }
}


// ------------------------------------customer_topic_1--------------------------------

@EnableBinding(MyStreams.class)
class customer_topic_1__a {

    @Autowired
    private MyStreams myStreams;

    static {
        System.out.println("静态代码块");
    }

    {
        System.out.println("构造代码块");
    }


    @StreamListener(MyStreams.MY_INPUT_1)
    public void receiveMsg(@Payload Msg msg) {

        System.out.println("-----------topic_1 customer_topic_1__a------------    " + JSON.toJSONString(msg));

        {
//            System.out.println("普通代码块");
        }
    }

}

@EnableBinding(MyStreams.class)
class customer_topic_1__b {

    @Autowired
    private MyStreams myStreams;


    @StreamListener(MyStreams.MY_INPUT_1)
    public void receiveMsg(@Payload Msg msg) {

        System.out.println("===========topic_1 customer_topic_1__b============    " + JSON.toJSONString(msg));
    }

}
// ------------------------------------customer_topic_1--------------------------------


// ====================================================================================
// ====================================================================================
// ====================================================================================


// ------------------------------------customer_topic_2--------------------------------

@EnableBinding(MyStreams.class)
class customer_topic_2 {

    @StreamListener(MyStreams.MY_INPUT_2)
    public void receiveMsg1(@Payload Msg msg) {

        System.out.println("-----------topic_2 customer1-----------    " + JSON.toJSONString(msg));
    }

    @StreamListener(MyStreams.MY_INPUT_2)
    public void receiveMsg2(@Payload Msg msg) {

        System.out.println("-----------topic_2 customer2-----------    " + JSON.toJSONString(msg));
    }


    public void receiveMsg(@Payload Msg msg) {

        System.out.println("-----------partner customer-----------    " + JSON.toJSONString(msg));

        // do something

    }
}
// ------------------------------------customer_topic_2--------------------------------


// ====================================================================================
// ====================================================================================
// ====================================================================================


// ------------------------------------POJO--------------------------------

@Data
class Msg {
    private Long id;
    private String name;
    private Integer age;
}