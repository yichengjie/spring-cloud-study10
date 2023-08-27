package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
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
    public void deferContextual() {
        Mono.just("Hello")
            .flatMap(value -> Mono.deferContextual(ctx -> Mono.just(value + ", "+ ctx.get("name"))))
            .contextWrite(ctx -> ctx.put("name", "World"))
            .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void zip(){
        Mono<String> hello = Mono.just("hello");
        Mono<String> world = Mono.just("World");
        Mono.zip(hello, world, (v1, v2) -> v1 + ", " + v2)
            .subscribe(value -> log.info("zip value : {}", value)) ;
    }


    // fromSupplier 与 defer 都能实现延迟执行的作用，只不过如参不一样
    @Test
    public void fromSupplier() throws InterruptedException {
        Mono<LocalDateTime> mono = Mono.fromSupplier(LocalDateTime::now);
        log.info("=========> init {}", LocalDateTime.now());
        TimeUnit.SECONDS.sleep(2);
        mono.subscribe(value -> log.info(" -> {}", value)) ;
        mono.subscribe(new Subscriber<LocalDateTime>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(1);
            }

            @Override
            public void onNext(LocalDateTime localDateTime) {
                log.info("value : {}", localDateTime);
            }

            @Override
            public void onError(Throwable t) {
                log.error("error : ", t);
            }

            @Override
            public void onComplete() {
                log.info("on complete");
            }
        });

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
    public void then(){
        Mono.defer(() -> Mono.just(1))
                .map(item -> {
                    log.info("item : {}", item) ;
                    return String.valueOf(item) ;
                })
                .then(Mono.fromRunnable(() -> log.info("save info ")))
                .then(Mono.fromRunnable(() -> log.info("publish refresh event ")))
                .subscribe()
        ;
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
