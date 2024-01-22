package com.yicj.study.ioc.service;

import com.yicj.study.ioc.HelloIocApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(classes = HelloIocApplication.class)
class HelloServiceTest {

    @Autowired
    private HelloService helloService ;

    @Test
    public void hello(){
        String retValue = helloService.hello("张三");
        log.info("ret value : {}", retValue);
    }
}