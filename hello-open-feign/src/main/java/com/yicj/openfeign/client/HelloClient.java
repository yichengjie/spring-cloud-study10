package com.yicj.openfeign.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "nacos-client-app"
//    , contextId = "echoClient"
    , url = "127.0.0.1:8081"
)
public interface HelloClient {

    @GetMapping("/hello/index")
    String index() ;

}
