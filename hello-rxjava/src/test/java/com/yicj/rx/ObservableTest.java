package com.yicj.rx;

import com.yicj.common.utils.CommonUtil;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/9 15:24
 */
@Slf4j
public class ObservableTest {

    @Test
    public void create(){
        Observable<String> observable = Observable.create(emitter -> {
            log.info("-----------");
            emitter.onNext("hello");
            emitter.onComplete();
        });
        observable.subscribe(item -> log.info("ret value : {}", item)) ;
        observable.subscribe(item -> log.info("ret value : {}", item)) ;
    }

    @Test
    public void timer(){
        Observable.timer(500, TimeUnit.MICROSECONDS)
                .subscribe(value -> log.info("value : {}", value)) ;
        CommonUtil.sleepQuiet(1000);
    }

    @Test
    public void interval(){
        Observable.interval(1_000_000/60, TimeUnit.MICROSECONDS)
                .subscribe((Long i) -> log.info("ret value : {}", i)) ;
        CommonUtil.sleepQuiet(2000);
    }
}
