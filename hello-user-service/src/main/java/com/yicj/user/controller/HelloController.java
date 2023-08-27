package com.yicj.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月27日 15:01
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public Mono<String> index(
            @RequestHeader(value = "x-token", required = false) String xtoken){
        log.info("x-token value : {}", xtoken);
        return Mono.fromSupplier(() -> "user service hello index !") ;
    }

}
