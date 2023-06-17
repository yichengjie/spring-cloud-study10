package com.yicj.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: yicj
 * @date: 2023/6/17 10:26
 */
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApp {

    public static void main(String[] args) {

        SpringApplication.run(EurekaClientApp.class, args) ;
    }
}
