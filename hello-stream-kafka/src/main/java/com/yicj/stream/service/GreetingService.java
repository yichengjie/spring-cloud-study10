package com.yicj.stream.service;

import com.yicj.stream.model.dto.Greetings;
import com.yicj.stream.support.GreetingsStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

/**
 * @author: yicj
 * @date: 2023/9/23 18:49
 */
@Slf4j
//@Service
//@EnableBinding(GreetingsStreams.class)
public class GreetingService {

    @Autowired
    private GreetingsStreams greetingsStreams;

    public void sendGreeting(final Greetings greetings) {
        log.info("Sending greetings {}", greetings);
        MessageChannel messageChannel = greetingsStreams.outboundGreetings();
        messageChannel.send(MessageBuilder
                .withPayload(greetings)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}
