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


    @Test
    public void filterWhen(){
//        Flux<Integer> flux = Flux.just(10,11,12,9).filterWhen(item -> Mono.just(item > 10));
//        flux.subscribe(item -> log.info("value : {}", item)) ;
        Integer[] fwArray = new Integer[]{1,2,3,4};

        Flux.fromArray(fwArray).filterWhen(item ->{
            return Mono.just(item % 2 == 0);
        }).subscribe(result ->{
            log.info("------> {}", result);
        });

        //测试二，结果 1 2 3 4，因为考虑Flux.just(true,false,false)
        //的第一个值true
        Flux.fromArray(fwArray).filterWhen(item ->{
            return Flux.just(true, false, false);
        }).subscribe(result ->{
            log.info("------> {}", result);
        });


        //测试三，结果空，因为考虑Flux.just(false, true, false)
        //的第一个值false
        Flux.fromArray(fwArray).filterWhen(item ->{
            return Flux.just(false, true, false);
        }).subscribe(result ->{
            log.info("------> {}", result);
        });
    }


    @Test
    public void filter(){
        Flux<Integer> flux = Flux.just(10,11,12,9).filter(item -> item > 10);
        flux.subscribe(item -> log.info("value : {}", item)) ;
    }



    private <R> Mono<R> createNotFoundError() {
        return Mono.defer(() -> {
            Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND);
            return Mono.error(ex);
        });
    }
}
