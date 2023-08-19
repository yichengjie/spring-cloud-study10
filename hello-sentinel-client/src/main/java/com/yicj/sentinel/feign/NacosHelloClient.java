package com.yicj.sentinel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "nacos-client-app", contextId = "nacosHelloClient")
public interface NacosHelloClient {

    @GetMapping("/hello/index")
    String hello() ;

}
