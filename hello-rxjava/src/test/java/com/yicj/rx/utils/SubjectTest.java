package com.yicj.rx.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import rx.subjects.PublishSubject;

/**
 * @author: yicj
 * @date: 2023/9/9 19:57
 */
@Slf4j
public class SubjectTest {

    @Test
    public void hello(){

        PublishSubject<Integer> subject = PublishSubject.create() ;

        subject.subscribe(item -> log.info("value : {}", item)) ;

        subject.onNext(1);

    }

}
