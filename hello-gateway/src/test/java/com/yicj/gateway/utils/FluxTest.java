package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public void reduceWith(){
        Flux<String> flux = Flux.just("学生1", "学生2", "学生3", "学生4", "学生5");
        flux.reduceWith(ArrayList::new, (list, item) ->{
            list.add(item) ;
            return list ;
        }).subscribe(item -> log.info("item value : {}",item)) ;
    }


    @Test
    public void reduce(){
        Flux<String> flux = Flux.just("学生1", "学生2", "学生3", "学生4", "学生5");
        flux.reduce((a,b) -> a +b)
                .subscribe(item -> log.info("item : {}", item)) ;
    }

    @Test
    public void reduceWithInitialValue(){
        Flux<String> flux = Flux.just("学生1", "学生2", "学生3", "学生4", "学生5");
        flux.reduce(new ArrayList<>(),(list,item) -> {
            list.add(item) ;
            return list ;
        }).subscribe(item -> log.info("item : {}", item)) ;
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
        log.info("------------------------------");
        //测试二，结果 1 2 3 4，因为考虑Flux.just(true,false,false)
        //的第一个值true
        Flux.fromArray(fwArray).filterWhen(item ->{
            return Flux.just(true, false, false);
        }).subscribe(result ->{
            log.info("------> {}", result);
        });
        log.info("------------------------------");
        //测试三，结果空，因为考虑Flux.just(false, true, false)
        //的第一个值false
        Flux.fromArray(fwArray).filterWhen(item ->{
            return Flux.just(false, true, false);
        })
        .subscribe(result ->{
            log.info("------> {}", result);
        });
    }

    @Test
    public void buffer(){
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list = flux.buffer(3).blockFirst();
        log.info("list : {}", list);
        //
        Integer blockFirst = flux.blockFirst();
        log.info("blockFirst : {}", blockFirst);
    }

    @Test
    public void switchIfEmpty(){
        Flux<Integer> flux = Flux.just(1, 2, 3, 4);
        flux.switchIfEmpty(Flux.just(7,8,9))
                .flatMap(item -> Mono.just(item))
                .subscribe(item -> log.info("item : {}", item)) ;
    }

    @Test
    public void switchIfEmpty2(){
        Flux<Integer> flux = Flux.just(1, 2, 3, 4);
        flux.flatMap(item -> Mono.just(item))
            // 注意这里switchIfEmpty 和 flatMap顺序问题
            .flatMap(item -> Mono.empty())
            .switchIfEmpty(Flux.just(7,8,9))
            .subscribe(item -> log.info("item : {}", item)) ;
    }


    @Test
    public void switchIfEmpty3(){
        Flux<Integer> flux = Flux.empty();
        flux.flatMap(item -> Mono.just(item))
                .switchIfEmpty(Flux.just(7,8,9))
                .subscribe(item -> log.info("item : {}", item)) ;
    }


    @Test
    public void switchIfEmpty4(){
        Flux<Integer> flux = Flux.empty();
        flux.switchIfEmpty(Flux.just(7,8,9))
                .flatMap(item -> Mono.just(item))
                .subscribe(item -> log.info("item : {}", item)) ;
    }

    private <R> Mono<R> createNotFoundError() {
        return Mono.defer(() -> {
            Exception ex = new ResponseStatusException(HttpStatus.NOT_FOUND);
            return Mono.error(ex);
        });
    }
}
