package com.yicj.webflux.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月26日 19:33
 */
@Slf4j
public class ServerResponseTest {

    @Test
    public void bodyValue(){
        ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("Hello world")
                .subscribe(value -> log.info("value : {}", value));
    }

    @Test
    public void body(){
        ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just("Hello world"), String.class)
                .subscribe(value -> log.info("value : {}", value));
    }


}
