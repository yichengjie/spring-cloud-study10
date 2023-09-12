package com.yicj.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yicj.sentinel.fallback.SentinelFallbackControllerFallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/sentinel/fallback")
public class SentinelFallbackController {

    @GetMapping("/exception")
    @SentinelResource(value = "sentinelFallbackException",
            fallback = "handleFallback",
            fallbackClass = SentinelFallbackControllerFallback.class
    )
    public String exception(Integer code){
        if (code %2 == 0){
            throw new RuntimeException("测试异常信息") ;
        }
        return "hello world " ;
    }


    @GetMapping("/ignore-exception")
    @SentinelResource(value = "sentinelFallbackException",
            fallback = "handleFallback",
            fallbackClass = SentinelFallbackControllerFallback.class,
            exceptionsToIgnore = {NullPointerException.class}
    )
    public String ignoreException(Integer code){
        if (code %2 == 0){
            throw new NullPointerException("测试异常信息") ;
        }
        return "hello world " ;
    }
}
