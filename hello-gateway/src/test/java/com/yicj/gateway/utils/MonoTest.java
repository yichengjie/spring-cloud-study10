package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023年08月23日 17:41
 */
@Slf4j
public class MonoTest {

    @Test
    public void defer() throws InterruptedException {
        Mono<LocalDateTime> mono = Mono.defer(() -> Mono.just(LocalDateTime.now()));
        log.info("=========> init {}", LocalDateTime.now());
        TimeUnit.SECONDS.sleep(2);
        mono.subscribe(value -> log.info(" -> {}", value)) ;
    }

    @Test
    public void filterWhen(){
        Integer value = 11 ;
        Mono<Integer> mono = Mono.just(value).filterWhen(item -> Mono.just(item > 10));
        mono.subscribe(item -> log.info("value : {}", item)) ;
    }


    @Test
    public void filter(){
        Integer value = 11 ;
        Mono<Integer> mono = Mono.just(value).filter(item -> item > 10);
        mono.subscribe(item -> log.info("value : {}", item)) ;
    }


    interface RoutePredicate<T>{

        Publisher<Boolean> apply(T t) ;
    }
}
