package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 9:53
 */
@Slf4j
public class ObservableTimerTest {

    @Test
    public void timer(){
        Observable.timer(1, TimeUnit.SECONDS)
                .flatMap(item -> Observable.range(1,5))
                .subscribe(value -> log.info("item {}", value)) ;
        CommonUtil.sleepQuiet(1100);
    }

    @Test
    public void timer2(){
        Observable.range(1,5)
                .flatMap(item -> Observable.timer(item, TimeUnit.MILLISECONDS).map(x -> item))
                .subscribe(value -> log.info("item {}", value)) ;
        CommonUtil.sleepQuiet(1100);
    }
}
