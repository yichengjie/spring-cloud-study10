package com.yicj.stream.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class ReceiveService {
 
    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        log.info("consumer receive message : {}", payload);
    }
}