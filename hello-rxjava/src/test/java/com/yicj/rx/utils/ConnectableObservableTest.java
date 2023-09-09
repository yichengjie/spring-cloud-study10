package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscription;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

/**
 * @author: yicj
 * @date: 2023/9/9 20:02
 */
@Slf4j
public class ConnectableObservableTest {

    @Test
    public void refCount(){
        Observable<String> create = Observable.create(subscriber -> {
            log.info("create .......");
            subscriber.onNext("test");
            subscriber.add(Subscriptions.create(() -> {
                log.info("close io resources");
            }));
        });
        Observable<String> lazy = create.publish().refCount();
        Subscription subscribe1 = lazy.subscribe(value -> log.info("subscribe1 value : {}", value));
        Subscription subscribe2 = lazy.subscribe(value -> log.info("subscribe2 value : {}", value));
        subscribe1.unsubscribe();
        subscribe2.unsubscribe();
        /**
         * create .......
         * subscribe1 value : test
         * close io resources
         */
    }

    @Test
    public void connect(){
        Observable<String> create = Observable.create(subscriber -> {
            log.info("create .......");
            subscriber.onNext("test");
            subscriber.add(Subscriptions.create(() -> {
                log.info("close io resources");
            }));
        });
        ConnectableObservable<String> connectableObservable = create.publish();
        log.info("subscribe1 sub start");
        Subscription subscribe1 = connectableObservable.subscribe(value -> log.info("subscribe1 value : {}", value));
        log.info("subscribe2 sub start");
        Subscription subscribe2 = connectableObservable.subscribe(value -> log.info("subscribe2 value : {}", value));
        //
        CommonUtil.sleepQuiet(1000);
        //
        log.info("connect ...");
        connectableObservable.connect() ;
        //
        subscribe1.unsubscribe();
        subscribe2.unsubscribe();
        CommonUtil.sleepQuiet(500);
        /**
         * subscribe1 sub start
         * subscribe2 sub start
         * connect ...
         * create .......
         * subscribe1 value : test
         * subscribe2 value : test
         */
    }
}
