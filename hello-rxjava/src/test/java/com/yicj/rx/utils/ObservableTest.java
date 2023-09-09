package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.internal.util.ActionSubscriber;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author: yicj
 * @date: 2023/9/9 19:22
 */
@Slf4j
public class ObservableTest {

    List<Subscriber<? super Integer>> subscribers = new CopyOnWriteArrayList<>() ;

    @Test
    public void create(){
        Observable<Integer> observable = Observable.unsafeCreate(subscriber -> subscriber.add(subscriber));

         Subscriber subscriber = new ActionSubscriber(value ->{

         }, error -> {},  () ->{

         }) ;

    }


    @Test
    public void flatMap(){
        Observable.just(1,2,3,4,5)
            .flatMap(
                item -> {
                    //return Observable.empty() ;
                    log.info("-----> flat map item : {}", item);
                    //return Observable.just(item) ;
                    return Observable.empty() ;
                },
                Observable::error,
                () -> {
                    log.info("complete ...");
                    return Observable.just(22) ;
                }
            ).subscribe(item -> log.info("ret value : {}", item)) ;

    }

    @Test
    public void subscribe(){
        Observable.just(1,2,3,4,5)
            .subscribe(
                item -> log.info("ret value : {}", item),
                error -> log.info("error :", error),
                () -> log.info("complete.....")
            ) ;
    }


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
