package com.yicj.stream.support;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface GreetingsStreams {
    String INPUT = "greetings-in";
    String OUTPUT = "greetings-out";

    /**
     * input
     * @return SubscribableChannel
     */
    @Input(INPUT)
    SubscribableChannel inboundGreetings();

    /**
     * output
     * @return MessageChannel
     */
    @Output(OUTPUT)
    MessageChannel outboundGreetings();
}
