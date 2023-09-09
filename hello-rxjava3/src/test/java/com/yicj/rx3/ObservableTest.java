package com.yicj.rx3;

import com.yicj.common.utils.CommonUtil;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
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
    public void create2(){

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

    @Test
    public void subject(){
        Observable<Integer> observable = Observable.just(1);
        //
        PublishSubject<Object> subject = PublishSubject.create();
        subject.subscribe(item -> log.info("subscribe1 value : {}", item)) ;
        subject.subscribe(item -> log.info("subscribe2 value : {}", item)) ;
        subject.onNext(1);
        CommonUtil.sleepQuiet(1000);
    }

}
