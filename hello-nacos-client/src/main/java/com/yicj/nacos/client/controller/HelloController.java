package com.yicj.nacos.client.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023年08月18日 13:26
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(){

        return "hello nacos client index !" ;
    }


    @GetMapping("/exception")
    public String exception(){
        throw new RuntimeException("测试nacos client异常！") ;
    }

}
