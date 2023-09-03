package com.yicj.gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author yicj
 * @date 2023年09月03日 11:14
 */
@Configuration
public class AppConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder() ;
    }


    @Bean
    public WebClient webClient(){

        return webClientBuilder().build() ;
    }

}
