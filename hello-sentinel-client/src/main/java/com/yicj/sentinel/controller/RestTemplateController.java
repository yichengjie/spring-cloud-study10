package com.yicj.sentinel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2023年09月12日 16:06
 */
@Slf4j
@RestController
@RequestMapping("/restTemplate/hello")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate ;

    @GetMapping("/index")
    public String index(){
        URI uri = UriComponentsBuilder.fromHttpUrl("http://nacos-client-app/hello/index")
                .build()
                .toUri();
        HttpMethod httpMethod = HttpMethod.GET;
        RequestEntity<String> requestEntity = new RequestEntity<>(httpMethod, uri);
        String retValue = restTemplate.exchange(requestEntity, String.class).getBody();
        log.info("ret value : {}", retValue);
        return retValue ;
    }

    @GetMapping("/exception")
    public String exception(){
        String retValue = restTemplate.getForObject("http://nacos-client-app/hello/exception", String.class);
        log.info("ret value : {}", retValue);
        return retValue ;
    }
}
