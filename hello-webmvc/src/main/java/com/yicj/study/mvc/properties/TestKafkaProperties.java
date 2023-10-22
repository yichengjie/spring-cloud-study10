//package com.yicj.study.mvc.properties;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//
//@Data
//@ConfigurationProperties(prefix = "hello.kafka")
//public class TestKafkaProperties implements Serializable {
//    private String bootstrapServers ;
//    private List<TestProducerProperties> producers = new ArrayList<>();
//
//    @Data
//    public static class TestProducerProperties implements Serializable {
//        private String username ;
//        private String password ;
//        private Integer retries = 0;
//        private Integer batchSize = 16384;
//        private Integer linger = 1;
//    }
//}