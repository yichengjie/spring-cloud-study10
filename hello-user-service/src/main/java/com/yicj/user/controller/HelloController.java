package com.yicj.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023年08月27日 15:01
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(){

        return "user service hello index !" ;
    }

}
