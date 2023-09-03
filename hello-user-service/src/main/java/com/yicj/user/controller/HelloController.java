package com.yicj.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author yicj
 * @date 2023年08月27日 15:01
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(
            @RequestHeader(value = "x-token", required = false) String xtoken){
        log.info("x-token value : {}", xtoken);
        return "user service hello index !" ;
    }

}
