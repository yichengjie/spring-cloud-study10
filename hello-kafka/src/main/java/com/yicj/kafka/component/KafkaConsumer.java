package com.yicj.kafka.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.kafka.model.message.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * <h1>Kafka 消费者</h1>
 * */
@Slf4j
@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper mapper;

    /**
     * <h2>监听 Kafka 消息并消费</h2>
     * */
    @KafkaListener(
        topics = {"hello-kafka-topic"},
        groupId = "hello-kafka-group",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void listener01(ConsumerRecord<String, String> record) throws Exception {
        String key = record.key();
        String value = record.value();
        HelloMessage kafkaMessage = mapper.readValue(value, HelloMessage.class);
        log.info("in listener consume kafka message: [{}], [{}]", key, mapper.writeValueAsString(kafkaMessage));
    }

}
