package com.yicj.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author: yicj
 * @date: 2023/8/20 10:15
 */
@RestController
@RequestMapping("/fallback")
public class HelloFallbackController {

    @RequestMapping("/index")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
