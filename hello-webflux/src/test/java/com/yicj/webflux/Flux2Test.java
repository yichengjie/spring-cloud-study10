package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Flux2Test {

    @Test
    public void generate(){
        Random random = new Random() ;
        Flux<Integer> flux = Flux.generate((Callable<List<Integer>>) ArrayList::new, (list, sink) -> {
            int value = random.nextInt(0, 20);
            log.info("gen value : {}", value);
            list.add(value);
            sink.next(value);
            if (list.size() == 5) {
                sink.complete();
            }
            return list;
        })
        .transform(fl -> {
            //fl.subscribe(value -> log.info("org value : {}", value)) ;
            return Mono.just(2) ;
        })
        ;
        flux.doOnError(error -> log.error("log global error : ", error))
            .subscribe(value -> log.info("value : {}", value));
    }

    @Test
    public void generate2(){
        Flux<Object> flux = Flux.generate(
                () -> 0, // 初始状态
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;// 改变状态
                },
                // 最后这个函数仅在最后执行一次，打印出 11
                state -> log.info("state : {}", state)
        );
        // 订阅时触发request -> sink.next顺序产生数据
        // 生产一个数据消费一个
        flux.subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void flatMap() throws InterruptedException {
        Flux.just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
                .flatMap(this::loadRecordFor, 2)
                .subscribe(value -> log.info("value : {}", value)) ;
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void concatMap() throws InterruptedException {
        Flux.just(DayOfWeek.SUNDAY, DayOfWeek.MONDAY)
                .concatMap(this::loadRecordFor)
                .subscribe(value -> log.info("value : {}", value)) ;
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void onErrorReturn(){
        Flux.just(1,2,3,4,5)
                .flatMap(item -> {
                    if (item == 3){
                        return Mono.error(new RuntimeException("数字3发生异常！！")) ;
                    }
                    return Mono.just("hello ["+item+"]") ;
                })
                .onErrorReturn("fallback value ")
                .subscribe(value -> log.info("value : {}", value)) ;
    }


    @Test
    public void onErrorReturnAdvance(){
        Flux.just(1,2,3,4,5)
            .flatMap(item -> {
                return this.sendEmail(item)
                    .ignoreElement()
                    .ofType(Integer.class)
                    .onErrorReturn(item)
                    .doOnError(e -> log.info("Failed to send {}", item))
                    //.subscribeOn(Schedulers.boundedElastic())
                    ;
            })
            .subscribe(value -> log.info("value : {}", value)) ;
    }

    private Mono<String> sendEmail(int item){
        return Mono.fromSupplier(() -> {
            if (item == 3){
                throw new RuntimeException("数字3发生异常！！") ;
            }
            return "hello["+item+"]" ;
        }) ;
    }



    private Flux<String> loadRecordFor(DayOfWeek daw){
        switch (daw){
            case SUNDAY:  return Flux.interval(Duration.ofMillis(90))
                    .take(5)
                    .map(i -> "Sun" + i) ;
            case MONDAY:  return Flux.interval(Duration.ofMillis(65))
                    .take(5)
                    .map(i -> "Mon" + i) ;
            default:  return Flux.empty() ;
        }
    }
}
