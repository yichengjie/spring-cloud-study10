package com.yicj.kafka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.kafka.component.KafkaProducer;
import com.yicj.kafka.model.message.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * <h1>SpringBoot 集成 kafka 发送消息</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final ObjectMapper mapper;
    private final KafkaProducer kafkaProducer;

    public KafkaController(ObjectMapper mapper, KafkaProducer kafkaProducer) {
        this.mapper = mapper;
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * <h2>发送 kafka 消息</h2>
     * */
    @GetMapping("/send-message")
    public void sendMessage(@RequestParam(required = false) String key,
                            @RequestParam String topic) throws Exception {
        HelloMessage message = new HelloMessage(1, "Hello-Kafka-Message");
        kafkaProducer.sendMessage(key, mapper.writeValueAsString(message), topic);
    }
}
