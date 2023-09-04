package com.yicj.webflux;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Slf4j
public class ErrorTest {


    @Test
    public void propagate(){
        Flux<String> flux = Flux.range(1, 10)
            .map(i -> {
                try {
                    return convert(i);
                } catch (IOException e) {
                    throw Exceptions.propagate(e);
                }
            });
        flux.subscribe(
            v -> log.info("received : {}", v),
            e -> {
                if (Exceptions.unwrap(e) instanceof IOException){
                    log.info("Something bod happened with I/O");
                }else {
                    log.info("Something bad happened");
                }
            }) ;
    }



    public String convert(int i) throws IOException {
        if (i > 3) {
            throw new IOException("boom " + i);
        }
        return "OK " + i;
    }
}
