package com.yicj.stream.listener;

import com.alibaba.fastjson.JSON;
import com.yicj.stream.model.dto.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@Slf4j
@EnableBinding(Sink.class)
public class DefaultReceiveService {
 
    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        HelloMessage message = JSON.parseObject(payload.toString(), HelloMessage.class);
        log.info("consumer receive message : {}", message);
    }
}