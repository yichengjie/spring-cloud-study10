package com.yicj.sentinel.fallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelFallbackControllerFallback {


    public static String handleFallback(Integer code, Throwable throwable){
        log.info("fallback exception :", throwable);
        return "exception fallback value !" ;
    }
}
