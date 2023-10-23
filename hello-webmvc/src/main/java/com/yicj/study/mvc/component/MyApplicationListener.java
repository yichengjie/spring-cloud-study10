package com.yicj.study.mvc.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;

/**
 * @author yicj
 * @date 2023/10/21 9:56
 */
@Slf4j
@Profile("dev")
public class MyApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    private static int staticIndex = 0 ;

    private int instanceIndex = 0 ;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        staticIndex ++ ;
        instanceIndex ++ ;
        log.info("-------> MyApplicationListener ---> {}, static execute index : {}, instance execute index :{}",
                event.getSource().getClass().getSimpleName(), staticIndex, instanceIndex);
    }
}
