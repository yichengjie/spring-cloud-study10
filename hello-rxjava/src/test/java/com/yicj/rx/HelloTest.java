package com.yicj.rx;

import io.reactivex.rxjava3.core.Observable;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author: yicj
 * @date: 2023/9/9 15:24
 */
@Slf4j
public class HelloTest {

    @Test
    public void hello(){
        Observable<String> observable = Observable.create(emitter -> {
            log.info("-----------");
            emitter.onNext("hello");
            emitter.onComplete();
        });
        observable.subscribe(item -> log.info("ret value : {}", item)) ;
        observable.subscribe(item -> log.info("ret value : {}", item)) ;
    }
}
