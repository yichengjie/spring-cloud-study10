package com.yicj.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    //@Autowired
    //private HttpMessageConverters httpMessageConverters ;

    @SentinelResource("HelloWorld")
    @GetMapping("/index")
    public String index(){
        return "hello index" ;
    }
}
