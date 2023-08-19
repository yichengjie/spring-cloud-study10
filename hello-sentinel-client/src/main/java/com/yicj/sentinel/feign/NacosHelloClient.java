package com.yicj.sentinel.feign;

import com.yicj.sentinel.feign.fallback.NacosHelloClientFallback;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@Qualifier("nacosHelloClient")
@FeignClient(value = "nacos-client-app"
        , contextId = "nacosHelloClient"
        , fallback = NacosHelloClientFallback.class
)
public interface NacosHelloClient {

    @GetMapping("/hello/index")
    String hello() ;

    @GetMapping("/hello/exception")
    String exception() ;
}
