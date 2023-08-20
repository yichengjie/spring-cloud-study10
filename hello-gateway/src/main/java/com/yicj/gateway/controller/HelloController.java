package com.yicj.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023年08月18日 13:05
 */
@RefreshScope
@RestController
@RequestMapping("/hello")
public class HelloController {

    //@Value("${nacos.username}")
    private String username ;

    @GetMapping("/index")
    public String index(){

        return "username : " + username ;
    }
}
