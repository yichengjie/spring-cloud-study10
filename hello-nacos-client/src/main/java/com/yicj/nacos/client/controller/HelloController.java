package com.yicj.nacos.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月18日 13:26
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private WebClient.Builder builder ;

    @GetMapping("/index")
    public String index(){

        return "hello nacos client index !" ;
    }

    @GetMapping("/exception")
    public String exception(){
        throw new RuntimeException("测试nacos client异常！") ;
    }

    @GetMapping("/user/index")
    public Mono<String> userIndex(){
        return builder.build()
                .get()
                .uri("http://hello-user-server/user-service/hello/index")
                //.uri("http://localhost:8085/user-service/hello/index")
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(String.class) ;
    }

}
