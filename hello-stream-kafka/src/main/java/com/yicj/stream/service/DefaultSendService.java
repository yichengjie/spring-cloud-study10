package com.yicj.stream.service;

import com.alibaba.fastjson.JSON;
import com.yicj.stream.model.dto.HelloMessage;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author: yicj
 * @date: 2023/9/23 19:08
 */
@EnableBinding(Source.class)
public class DefaultSendService {

    private final Source source;

    public DefaultSendService(Source source) {
        this.source = source;
    }

    public void sendMsg(HelloMessage message) {
        String content = JSON.toJSONString(message);
        source.output().send(MessageBuilder.withPayload(content).build());
    }
}
