package com.yicj.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author yicj
 * @date 2023年08月23日 17:41
 */
@Slf4j
public class MonoTest {

    @Test
    public void defer() throws InterruptedException {
        Mono<LocalDateTime> mono = Mono.defer(() -> Mono.just(LocalDateTime.now()));
        log.info("=========> init {}", LocalDateTime.now());
        TimeUnit.SECONDS.sleep(2);
        mono.subscribe(value -> log.info(" -> {}", value)) ;
    }
}
