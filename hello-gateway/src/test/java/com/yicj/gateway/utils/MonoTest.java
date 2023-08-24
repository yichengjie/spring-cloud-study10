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

    @Test
    public void other() throws InterruptedException {
        Mono<LocalDateTime> mono = Mono.just(1)
                .map(item -> {
                    if (item == 1) {
                        throw new RuntimeException("测试异常");
                    }
                    return item;
                })
                .doOnSuccess(value -> log.info("value : {}", value))
                .onErrorResume(ex -> {
                    log.error("error : ", ex);
                    return Mono.just(2);
                })
                .flatMap(item -> {
                    log.info("flat map convert value : {}", item);
                    return Mono.just(LocalDateTime.now());
                })
                .then(Mono.defer(() -> Mono.just(LocalDateTime.now())));
        log.info("define time : {}", LocalDateTime.now());
        Thread.sleep(2000);
        mono.subscribe(item -> log.info("item -> {}", item));
        Thread.sleep(1000);
        //.doOnSuccess(aVoid -> logResponse(exchange))
        //.onErrorResume(ex -> handleUnresolvedError(exchange, ex))
        //.then(exchange.cleanupMultipart())
        //.then(Mono.defer(response::setComplete));
    }


    interface RoutePredicate<T>{

        Publisher<Boolean> apply(T t) ;
    }
}
