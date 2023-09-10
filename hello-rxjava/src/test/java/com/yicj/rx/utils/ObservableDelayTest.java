package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 9:44
 */
@Slf4j
public class ObservableDelayTest {

    @Test
    public void delay(){
        Observable.range(1,5)
                .delay(1, TimeUnit.SECONDS)
                .subscribe(value -> log.info("item {}", value)) ;
        CommonUtil.sleepQuiet(1100);
    }

    @Test
    public void delay2(){
        Observable.range(1,5)
                .delay(item -> Observable.timer(item, TimeUnit.MILLISECONDS))
                .subscribe(value -> log.info("item {}", value)) ;
        CommonUtil.sleepQuiet(1100);
    }


    @Test
    public void delay3(){
        Observable<String> ob2 = Observable.range(6, 5)
                .flatMap(item ->
                    Observable.just(item)
                        .delay(220, TimeUnit.MILLISECONDS)
                        .map(x -> "ob1 with value : [" + x + "]")
                )

                ;
        ob2.subscribe(value -> log.info("ret value : {}", value)) ;
        CommonUtil.sleepQuiet(250);
    }

    @Test
    public void delay4(){
        Observable<String> ob2 = Observable.range(6, 5)
                .flatMap(item -> Observable.just(item)
                        .map(x -> "ob1 with value : [" + x + "]")
                        .delay(220, TimeUnit.MILLISECONDS)
                )
                ;
        ob2.subscribe(value -> log.info("ret value : {}", value)) ;
        CommonUtil.sleepQuiet(250);
    }
}
