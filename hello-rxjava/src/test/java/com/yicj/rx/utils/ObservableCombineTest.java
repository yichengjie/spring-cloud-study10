package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 11:13
 */
@Slf4j
public class ObservableCombineTest {

    @Test
    public void combineLatest(){
        Observable<String> slow = Observable.interval(17, TimeUnit.MILLISECONDS)
                .map(x -> "S" + x);
        Observable<String> fast = Observable.interval(10, TimeUnit.MILLISECONDS)
                .map(x -> "F" + x);
        Observable.combineLatest(slow, fast, (s, f) -> f + " : " + s)
                .forEach(value -> log.info("ret value : {}", value));
        CommonUtil.sleepQuiet(1000);
    }


}
