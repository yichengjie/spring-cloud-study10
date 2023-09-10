package com.yicj.rx.utils;

import com.yicj.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;
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
    public void subscribe(){
        Observable.just(1,2,3,4,5)
            .subscribe(
                item -> log.info("ret value : {}", item),
                error -> log.info("error :", error),
                () -> log.info("complete.....")
            ) ;
    }


    @Test
    public void create(){
        Observable<Integer> observable = Observable.unsafeCreate(subscriber -> subscriber.add(subscriber));

        Subscriber subscriber = new ActionSubscriber(value ->{

        }, error -> {},  () ->{

        }) ;

    }

}
