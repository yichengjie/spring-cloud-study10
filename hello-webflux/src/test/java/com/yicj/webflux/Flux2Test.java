package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

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
}
