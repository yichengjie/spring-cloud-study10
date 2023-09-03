package com.yicj.webflux;

import com.yicj.webflux.repository.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author yicj
 * @date 2023年08月26日 19:52
 */
@Slf4j
public class MonoTest {

    @Test
    public void switchIfEmpty(){
        Mono.just(1)
            .switchIfEmpty(Mono.error(new RuntimeException("数据不存在!")))
            .subscribe(value -> log.info("value : {}",value)) ;
    }

    @Test
    public void doOnError(){
        Mono.error(new RuntimeException("test error !!!"))
                .map(value -> 1)
                .doOnError(error -> {
                    log.error("错误异常处理 ", error);
                }).subscribe() ;
    }

    @Test
    public void onErrorResume(){
        Mono.error(new RuntimeException("test error !!!"))
                .map(value -> 1)
                .onErrorResume(error -> {
                    log.error("错误异常处理 ", error);
                    return Mono.empty() ;
                }).subscribe() ;
    }

    /**
     * 当数据不存在时候报错提示
     */
    @Test
    @SuppressWarnings("all")
    public void switchIfEmptyError(){
        Mono.just(1)
                .then()
                .switchIfEmpty(Mono.error(new RuntimeException("数据不存在!")))
                .subscribe(value -> log.info("value : {}",value)) ;
    }


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

    @Test
    public void thenMany(){
        Mono.just("hello")
            .thenMany(Flux.just(1,2,3))
            .subscribe(value -> log.info("value : {}",value)) ;
    }

    @Test
    public void flatMap(){
        Mono.just("hello")
            .flatMap(value -> Mono.just(value.length()))
            .subscribe(value -> log.info("value : {}",value)) ;
    }

    // mono 转flux
    @Test
    public void flatMapMany(){
        Mono.just("hello")
            .flatMapMany(value -> {
                char[] charArray = value.toCharArray();
                List<String> list = new ArrayList<>() ;
                for (char v: charArray){
                    list.add(String.valueOf(v)) ;
                }
                return Flux.fromIterable(list) ;
            }).subscribe(value -> log.info("value : {}",value)) ;
    }

    @Test
    public void thenEmpty(){
        Mono.just(1)
            .thenEmpty(Mono.fromCallable(() -> {
                log.info("向前端页面输出错误信息...");
                return false ;
            }).then())
            .subscribe();
    }

    @Test
    @SuppressWarnings("all")
    public void thenEmpty2(){
        Mono.just(1)
            .thenEmpty(Mono.empty())
            .subscribe(value -> log.info("value : {}", value));
    }

    @Test
    public void then(){
        Mono.just(1)
            .then(Mono.fromCallable(() -> 1))
            .subscribe(value -> log.info("value :{}", value)) ;
    }

    @Test
    public void monoVoid(){
        Mono.just(1)
            .flatMap(item -> {
                log.info("flat map1 value : {}", item);
                if (item % 2 == 0){
                    return Mono.just(item) ;
                }
                //return Mono.just(item).then() ;
                return Mono.empty();
            })
            .flatMap(item -> {
                log.info("flat map2 value : {}", item);
                return Mono.just(1) ;
            }).subscribe(value -> log.info("value : {}",value)) ;
    }

    @Test
    public void zipWith(){
        Mono.just("hello")
            .flatMap(value -> Mono.just(value.toUpperCase()))
            .flatMap(value -> {
                log.info("value2 : {}", value);
                PersonEntity p1 = new PersonEntity() ;
                p1.setId("1");
                return Mono.just(value).zipWhen(v -> Mono.just(p1)) ;
            })
            .flatMap(value -> Mono.just(value.getT1() + ", " + value.getT2()))
            .subscribe(value -> log.info("value : {}", value)) ;
    }
}
