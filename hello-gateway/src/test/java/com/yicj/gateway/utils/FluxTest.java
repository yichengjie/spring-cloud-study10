package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023年08月23日 13:02
 */
@Slf4j
public class FluxTest {

    @Test
    public void hello(){
        Flux<Integer> flux = Flux.just(1);
        flux.map(String::valueOf)
                .subscribe(item -> log.info("===== {}", item)) ;
        System.out.println("-------------");
        flux.concatMap(Flux::just)
                .subscribe(item -> log.info("===== {}", item)) ;
    }

    @Test
    public void flatMap() throws Exception{
        Flux.just("学生1", "学生2", "学生3", "学生4", "学生5")
                .flatMap(item -> Mono.just(item).delayElement(Duration.ofMillis(200)))
                .subscribe(item -> log.info("===== {}", item));
        Thread.sleep(2000);
    }

    @Test
    public void concatMap() throws Exception{
        Flux.just("学生1", "学生2", "学生3", "学生4", "学生5")
                //.concatMap(item -> Mono.just(item).delayElement(Duration.ofMillis(200)))
                .concatMap(item -> Mono.defer(() -> Mono.just(item)))
                .subscribe(item -> log.info("===== {}", item));
        Thread.sleep(2000);
    }


    @Test
    public void next() throws Exception{
        Flux.just("学生1", "学生2", "学生3", "学生4", "学生5")
                .concatMap(item -> Mono.just(item).delayElement(Duration.ofMillis(200)))
                .next()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .subscribe(item -> log.info("===== {}", item));
        Thread.sleep(2000);
    }

    private <R> Mono<R> createNotFoundError() {
        return Mono.defer(() -> {
            Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND);
            return Mono.error(ex);
        });
    }
}
