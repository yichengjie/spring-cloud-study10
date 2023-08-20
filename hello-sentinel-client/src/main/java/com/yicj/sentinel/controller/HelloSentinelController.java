package com.yicj.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sentinel/hello")
public class HelloSentinelController {

    @GetMapping("/index")
    public String index(){

        return "hello sentinel client index !" ;
    }

}
