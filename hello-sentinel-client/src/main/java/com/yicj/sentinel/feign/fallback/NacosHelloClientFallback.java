package com.yicj.sentinel.feign.fallback;

import com.yicj.sentinel.feign.NacosHelloClient;
import org.springframework.stereotype.Component;

@Component
public class NacosHelloClientFallback implements NacosHelloClient {

    @Override
    public String hello() {
        return "fallback hello ret value";
    }

    @Override
    public String exception() {
        return "fallback exception ret value";
    }
}
