package com.yicj.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author yicj
 * @date 2023年08月30日 17:25
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class OpenFeignApplication {

    public static void main(String[] args) {

        SpringApplication.run(OpenFeignApplication.class, args) ;
    }
}
