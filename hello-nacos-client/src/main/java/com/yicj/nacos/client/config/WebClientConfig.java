package com.yicj.nacos.client.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;

/**
 * @author yicj
 * @date 2023年08月27日 9:34
 */
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder builder(){
        return WebClient.builder()
                .filter((request, next) -> {
                    ClientRequest newRequest =  ClientRequest.from(request)
                            .header("x-token", "bar")
                            .build();
                    return next.exchange(newRequest);
                });
    }

}
