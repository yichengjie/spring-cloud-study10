package com.yicj.stream.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author: yicj
 * @date: 2023/9/23 19:08
 */
@EnableBinding(Source.class)
public class SendService {
    @Autowired
    private Source source;

    public void sendMsg(String msg) {
        source.output().send(MessageBuilder.withPayload(msg).build());
    }
}
