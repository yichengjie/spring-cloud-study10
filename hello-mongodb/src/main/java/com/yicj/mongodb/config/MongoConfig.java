package com.yicj.mongodb.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@Configuration
public class MongoConfig {

//    @Bean
//    public MongoClient mongoClient() {
//        return MongoClients.create("mongodb://localhost:27017");
//    }

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        mongo.setPort(27017);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToConnectionPoolSettings(setting -> {
                    setting.maxSize(10) ;
                    setting.minSize(1) ;
                })
                .build();
        mongo.setMongoClientSettings(settings);
        return mongo;
    }
}
