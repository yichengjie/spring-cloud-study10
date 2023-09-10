package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 9:52
 */
@Slf4j
public class ObservableMergeTest {

    /**
     * merge不保证顺序
     */
    @Test
    public void merge(){
        Observable<String> ob1 = Observable.range(1, 5)
                .flatMap(item -> Observable.timer(230, TimeUnit.MILLISECONDS)
                        .map(x -> "ob1 with value : [" + item + "]"));
        Observable<String> ob2 = Observable.range(6, 5)
                .flatMap(item -> Observable.just(item)
                        .map(x -> "ob1 with value : [" + x + "]")
                        .delay(220, TimeUnit.MILLISECONDS)
                ) ;
        Observable.merge(ob1, ob2)
                .subscribe(value -> log.info("ret value : {}", value)) ;
        CommonUtil.sleepQuiet(300);
    }

    @Test
    public void mergeWith(){
        Observable<String> ob1 = Observable.range(1, 5)
                .flatMap(item -> Observable.timer(230, TimeUnit.MILLISECONDS)
                        .map(x -> "ob1 with value : [" + item + "]"));
        Observable<String> ob2 = Observable.range(6, 5)
                .flatMap(item -> Observable.just(item)
                        .map(x -> "ob1 with value : [" + x + "]")
                        .delay(220, TimeUnit.MILLISECONDS)
                ) ;
        ob1.mergeWith(ob2)
                .subscribe(value -> log.info("ret value : {}", value)) ;
        CommonUtil.sleepQuiet(300);
    }

    @Test
    public void hello(){
        Observable.just(null)
                .subscribe(value -> log.info( "value : {}", value)) ;
    }

}
