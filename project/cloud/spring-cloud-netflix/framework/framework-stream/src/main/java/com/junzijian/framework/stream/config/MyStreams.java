package com.junzijian.framework.stream.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author bebopze
 * @date 2019/2/26
 */
public interface MyStreams {

    String MY_INPUT_1 = "my_input_1";
    String MY_OUTPUT_1 = "my_output_1";

    String MY_INPUT_2 = "my_input_2";
    String MY_OUTPUT_2 = "my_output_2";


    @Input(MY_INPUT_1)
    SubscribableChannel my_input_1();

    @Output(MY_OUTPUT_1)
    MessageChannel my_output_1();


    @Input(MY_INPUT_2)
    SubscribableChannel my_input_2();

    @Output(MY_OUTPUT_2)
    MessageChannel my_output_2();
}
