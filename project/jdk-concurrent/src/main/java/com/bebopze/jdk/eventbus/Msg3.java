package com.bebopze.jdk.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息类型3
 *
 * @author bebopze
 * @date 2020/8/10
 */
@Data
@AllArgsConstructor
public class Msg3 {

    private Long id;

    private String msg;
}
