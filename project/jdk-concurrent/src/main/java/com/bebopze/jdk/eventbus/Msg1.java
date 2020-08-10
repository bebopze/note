package com.bebopze.jdk.eventbus;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 消息类型1
 *
 * @author bebopze
 * @date 2020/8/10
 */
@Data
@AllArgsConstructor
public class Msg1 {

    private Long id;

    private String msg;
}
