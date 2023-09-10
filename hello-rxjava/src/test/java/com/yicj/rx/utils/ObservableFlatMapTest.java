package com.yicj.rx.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.Observable;

/**
 * @author: yicj
 * @date: 2023/9/10 9:54
 */
@Slf4j
public class ObservableFlatMapTest {

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
    public void flatMap2(){
        Observable<Integer> oneToEight = Observable.range(1, 8);
        Observable<String> ranks = oneToEight
                .map(Object::toString);
        Observable<String> files = oneToEight
                .map(x -> 'a' + x - 1)
                .map(ascii -> (char) ascii.intValue())
                .map(ch -> Character.toString(ch));
        Observable<String> squares = files
                .flatMap(file -> ranks.map(rank -> file + rank));
        squares.subscribe(value -> log.info("ret value : {}", value)) ;
        /**
         * a1
         * a2
         * ...
         * b1
         * b2
         * ...
         * c1
         * c2
         */
    }

}
