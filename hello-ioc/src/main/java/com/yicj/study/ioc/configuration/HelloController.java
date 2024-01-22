package com.yicj.study.ioc.configuration;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023/11/5 11:27
 */
@RestController
public class HelloController {

    @GetMapping("/hello/index")
    public String hello(){

        return "Hello index" ;
    }

}
