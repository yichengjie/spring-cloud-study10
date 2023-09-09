package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Mono2Test {

    @Test
    public void subscribeOn() throws InterruptedException {
        Mono.just(1)
                .map(item -> {
                    log.info("map item : {}", item);
                    return "hello["+item+"]" ;
                })
                .then(Mono.defer(() -> Mono.just(2).subscribeOn(Schedulers.boundedElastic())))
                //.subscribeOn(Schedulers.parallel())
                .subscribe(value -> log.info("result value : {}", value)) ;
        Thread.sleep(1000);
    }


    @Test
    public void cache(){
        Mono<Object> mono = Mono.create(monoSink -> {
            log.info("----------");
            monoSink.success("hello");
        })
        .cache()
        ;
        //
        mono.subscribe(value -> log.info("value : {}", value)) ;
        mono.subscribe(value -> log.info("value : {}", value)) ;
    }

}
