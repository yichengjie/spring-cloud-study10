package com.yicj.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel/datasource")
public class SentinelDatasourceController {

    @GetMapping("/index")
    @SentinelResource("sentinelDatasourceIndex")
    public String index(){

        return "hello world index" ;
    }
}
