package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.concurrent.CompletableFuture;

/**
 * @author yicj
 * @date 2023年09月02日 16:10
 */
@Slf4j
public class DeferContextualTest {

    @Test
    public void hello(){
        String key = "message";
        Mono<String> mono = Mono.just("Hello")
            .flatMap(s ->
                Mono.deferContextual(ctx ->{
                    //return Mono.just(s + " " + ctx.get(key)) ;
                    return Mono.<String>create(monoSink -> {
                        CompletableFuture.runAsync(() -> {
                            log.info("ctx value : {}", (String)ctx.get(key));
                            monoSink.success(s + " " + ctx.get(key)) ;
                        }) ;
                    });
                })
            )
            .contextWrite(ctx -> ctx.put(key, "World"));
        // subscribe
        mono
            .publishOn(Schedulers.parallel())
            .subscribe(value -> log.info("value : {}", value)) ;

        StepVerifier.create(mono)
            .expectNext("Hello World")
            .verifyComplete();
    }


    @Test
    public void hello2(){
        String key = "message";
        Mono<String> mono = Mono.just("Hello")
                // contextWrite 会上上游传播，下游获取不到值
                .contextWrite(ctx -> ctx.put(key, "World"))
                .flatMap( s ->
                    Mono.deferContextual(ctx ->{
                        log.info("ctx value : {}", (String)ctx.getOrDefault(key, "Stranger"));
                        return Mono.just(s + " " + ctx.getOrDefault(key, "Stranger")) ;
                    }
                )) ;
                //.contextWrite(ctx -> ctx.put(key, "Test"));
        //
        mono.subscribe(value -> log.info("value : {}", value)) ;
        //
        StepVerifier.create(mono)
                .expectNext("Hello Stranger")
                .verifyComplete();
    }

    @Test
    public void hello3(){
        String key = "message";
        Mono<String> r = Mono
                .deferContextual(ctx -> Mono.just("Hello " + ctx.get(key)))
                .contextWrite(ctx -> ctx.put(key, "Reactor"))
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello Reactor")
                .verifyComplete();
    }

    @Test
    public void hello4(){
        String key = "message";
        Mono<String> r = Mono
                .deferContextual(ctx -> Mono.just("Hello " + ctx.get(key)))
                .contextWrite(ctx -> ctx.put(key, "Reactor"))
                //
                .flatMap( s -> Mono.deferContextual(ctx ->
                        Mono.just(s + " " + ctx.get(key))))
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello Reactor World")
                .verifyComplete();
    }

    @Test
    public void hello5(){
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap( s -> Mono
                        .deferContextual(ctxView -> Mono.just(s + " " + ctxView.get(key)))
                )
                .flatMap( s -> Mono
                        .deferContextual(ctxView -> Mono.just(s + " " + ctxView.get(key)))
                        .contextWrite(ctx -> ctx.put(key, "Reactor"))
                )
                .contextWrite(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello World Reactor")
                .verifyComplete();
    }
}
