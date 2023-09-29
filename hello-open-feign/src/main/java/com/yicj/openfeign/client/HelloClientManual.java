package com.yicj.openfeign.client;

import org.springframework.web.bind.annotation.GetMapping;

public interface HelloClientManual {

    @GetMapping("/hello/index")
    String index() ;
}
