package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author yicj
 * @date 2023年08月26日 19:52
 */
@Slf4j
public class FluxTest {

    @Test
    public void reduce(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(new ArrayList<Integer>(), (list, item) -> {
            list.add(item) ;
            return list ;
        }).subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce2(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(0, Integer::sum)
                .subscribe(value -> log.info("value : {}", value)) ;
    }

    @Test
    public void reduce3(){
        Flux<Integer> flux = Flux.fromIterable(Arrays.asList(1,2,3,4,5,6)) ;
        flux.reduce(Integer::sum)
                .subscribe(value -> log.info("value : {}", value)) ;
    }

}
