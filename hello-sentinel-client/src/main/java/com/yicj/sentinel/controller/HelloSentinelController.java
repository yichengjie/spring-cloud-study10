package com.yicj.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/sentinel/hello")
public class HelloSentinelController {

    @Autowired
    public RestTemplate restTemplate ;

    @GetMapping("/index")
    public String index(){
        return "hello sentinel client index !" ;
    }


}
