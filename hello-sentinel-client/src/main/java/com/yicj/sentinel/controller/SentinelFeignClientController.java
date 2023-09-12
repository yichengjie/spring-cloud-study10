package com.yicj.sentinel.controller;

import com.yicj.sentinel.feign.NacosHelloClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author yicj
 * @date 2023年09月12日 16:15
 */
@Slf4j
@RestController
@RequestMapping("/feignClient/hello")
public class SentinelFeignClientController {

    @Autowired
    @Qualifier("nacosHelloClient")
    private NacosHelloClient nacosHelloClient ;

    @GetMapping("/index")
    public String index(){
        String retValue = nacosHelloClient.hello();
        log.info("ret value : {}", retValue);
        return retValue ;
    }

    @GetMapping("/exception")
    public String exception(){
        String retValue = nacosHelloClient.exception() ;
        log.info("ret value : {}", retValue);
        return retValue ;
    }
}
