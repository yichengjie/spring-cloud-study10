package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/10 11:36
 */
@Slf4j
public class ObservableAmbTest {


    @Test
    public void abm1(){
        Observable.amb(
            stream(100, 17, "S"),
            stream(200, 17, "F")
        ).subscribe(log::info) ;
        CommonUtil.sleepQuiet(1000);
    }


    private Observable<String> stream(int initialDelay, int interval, String name){
        return Observable.interval(initialDelay, interval, TimeUnit.MILLISECONDS)
                .map(x -> name + x)
                .doOnSubscribe(() -> log.info("Subscribe to " + name))
                .doOnUnsubscribe(() -> log.info("Unsubscribe from " + name)) ;
    }

}
