package com.yicj.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yicj
 * @date 2023年09月13日 12:57
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SentinelGatewayApplication.class, args) ;
    }
}
