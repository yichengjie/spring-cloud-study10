package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月26日 19:52
 */
@Slf4j
public class MonoTest {

    @Test
    public void fromSupplier(){
        Mono.fromSupplier(() -> "hello world")
                .subscribe(item -> log.info("value : {}", item));
        log.info("---------------------");
        Mono.fromSupplier(() -> Mono.just("hello world"))
                .subscribe(item -> log.info("value : {}", item));
    }


    @Test
    public void defer(){
        Mono.defer(() -> Mono.just("hello world"))
                .subscribe(item -> log.info("value : {}", item));
    }
}
