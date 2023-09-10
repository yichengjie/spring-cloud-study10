package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 11:25
 */
@Slf4j
public class ObservableWithLatestFromTest {

    @Test
    public void withLatestFrom(){
        Observable<String> slow = Observable.interval(17, TimeUnit.MILLISECONDS)
                .map(x -> "S" + x);
        Observable<String> fast = Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(x -> "F" + x);
        slow.withLatestFrom(fast, (s, f) -> s + " : " + f)
                .forEach(value -> log.info("ret value : {}", value));
        CommonUtil.sleepQuiet(1000);
    }

    @Test
    public void withLatestFrom2(){
        Observable<String> fast = Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(x -> "F" + x)
                .delay(500, TimeUnit.MICROSECONDS)
                .startWith("FX")
                ;
        Observable<String> slow = Observable.interval(17, TimeUnit.MILLISECONDS)
                .map(x -> "S" + x);
        slow.withLatestFrom(fast, (s, f) -> s + " : " + f)
                .forEach(value -> log.info("ret value : {}", value));
        CommonUtil.sleepQuiet(500);
    }

}
