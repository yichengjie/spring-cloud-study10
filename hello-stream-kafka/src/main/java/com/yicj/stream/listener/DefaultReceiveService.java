package com.yicj.stream.listener;

import com.alibaba.fastjson.JSON;
import com.yicj.stream.model.dto.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@EnableBinding(Sink.class)
public class DefaultReceiveService {
 
//    @StreamListener(Sink.INPUT)
//    public void receive(@Payload Object payload){
//        HelloMessage message = JSON.parseObject(payload.toString(), HelloMessage.class);
//        log.info("========> consumer receive message : {}", message);
//    }


    @StreamListener(Sink.INPUT)
    public void process(Message<?> message) {
        Acknowledgment acknowledgment = message.getHeaders().get(KafkaHeaders.ACKNOWLEDGMENT, Acknowledgment.class);
        if (acknowledgment != null) {
            System.out.println("Acknowledgment provided");
            acknowledgment.acknowledge();
        }
    }
}