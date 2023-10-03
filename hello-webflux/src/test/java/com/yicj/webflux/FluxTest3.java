package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年10月03日 15:39
 */
@Slf4j
public class FluxTest3 {

    @Test
    public void switchIfEmpty(){
        Flux.empty()
                .next()
                .switchIfEmpty(Mono.fromSupplier(() -> 1))
                .subscribe(value -> log.info("value : {}", value)) ;
    }


    @Test
    public void switchIfEmpty2(){
        Flux.empty()
                .switchIfEmpty(Mono.fromSupplier(() -> 1))
                .next()
                .subscribe(value -> log.info("value : {}", value)) ;
    }
}
