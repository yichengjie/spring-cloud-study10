package com.yicj.rx.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;
import rx.Subscriber;
import rx.internal.util.ActionSubscriber;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
}
