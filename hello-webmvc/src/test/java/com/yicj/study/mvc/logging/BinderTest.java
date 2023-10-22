package com.yicj.study.mvc.logging;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.logging.LogLevel;
import org.springframework.core.ResolvableType;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.util.MultiValueMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author yicj
 * @date 2023/10/21 12:33
 */
@Slf4j
public class BinderTest {

    private MockEnvironment env = null ;

    private static final ConfigurationPropertyName LOGGING_GROUP = ConfigurationPropertyName.of("logging.group");
    private static final Bindable<Map<String, List<String>>> STRING_STRINGS_MAP = Bindable
            .of(ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, String.class).asMap());
    //
    private static final ConfigurationPropertyName LOGGING_LEVEL = ConfigurationPropertyName.of("logging.level");
    private static final Bindable<Map<String, LogLevel>> STRING_LOGLEVEL_MAP = Bindable.mapOf(String.class, LogLevel.class);

    @BeforeEach
    public void before(){
        env = new MockEnvironment();
        env.setProperty("hello.kafka.bootstrapServers", "https://hello.com");
        env.setProperty("hello.kafka.producers[0].username", "username123");
        env.setProperty("hello.kafka.producers[0].password", "password123");
        //
        env.setProperty("hello.kafka.producers[1].username", "username456");
        env.setProperty("hello.kafka.producers[1].password", "password456");
        //logging.level
        env.setProperty("logging.level.org.springframework.boot", "DEBUG");
        env.setProperty("logging.level.org.springframework", "TRACE");
        env.setProperty("logging.level.org.apache.tomcat", "TRACE");
        // logging.group
        env.setProperty("logging.group[0].web", "org.springframework.core.codec");
        env.setProperty("logging.group[1].web", "org.springframework.http");
        env.setProperty("logging.group[2].web", "org.springframework.web");
        env.setProperty("logging.group[0].sql", "org.springframework.jdbc.core");
        env.setProperty("logging.group[1].sql", "org.hibernate.SQL");
    }

    @Test
    public void bindOrElseGet(){
        Binder binder = Binder.get(env);
        Map<String, LogLevel> levels = binder.bind(LOGGING_LEVEL, STRING_LOGLEVEL_MAP)
                .orElseGet(Collections::emptyMap);
        levels.forEach((key, value) -> log.info("key : {}, value : {}", key, value));
    }

    @Test
    public void bindIfBound(){
        Binder binder = Binder.get(env);
        binder.bind(LOGGING_GROUP, STRING_STRINGS_MAP).ifBound((Map<String, List<String>> map) -> {
            log.info("value : {}", map) ;
            map.forEach((key, value) -> log.info("key : {}, value : {}", key, value));
        });
    }

    /**
     * 属性前缀绑定
     */
    @Test
    public void bindWithPrefix(){
        BindResult<TestKafkaProperties> kafkaPropertiesBindResult =
                Binder.get(env).bind("hello.kafka", TestKafkaProperties.class);
        TestKafkaProperties kafkaProperties = kafkaPropertiesBindResult.get();
        List<TestKafkaProperties.TestProducerProperties> producers = kafkaProperties.getProducers();
        producers.forEach(item -> log.info("item : {}", item));
    }

    // 如果是内部类这里需要使用static修饰，否则会报错
    @Data
    public static class TestKafkaProperties implements Serializable {
        private String bootstrapServers ;
        private List<TestKafkaProperties.TestProducerProperties> producers = new ArrayList<>();

        @Data
        public static class TestProducerProperties implements Serializable {
            private String username ;
            private String password ;
            private Integer retries = 0;
            private Integer batchSize = 16384;
            private Integer linger = 1;
        }
    }

}
