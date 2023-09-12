package com.yicj.sentinel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author yicj
 * @date 2023年09月12日 15:06
 */
@Configuration
public class SentinelConfig {

    @Bean
    public RestTemplate restTemplate(){

        return new RestTemplate() ;
    }

}
