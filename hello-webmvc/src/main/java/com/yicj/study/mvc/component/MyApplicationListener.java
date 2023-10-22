package com.yicj.study.mvc.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author yicj
 * @date 2023年10月20日 10:13
 */
@Slf4j
public class MyApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    private int count = 0;
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        log.info("ApplicationReadyEvent execute index : [{}]", count ++);
    }
}
