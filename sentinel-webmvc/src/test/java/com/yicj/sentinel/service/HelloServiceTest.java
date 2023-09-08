package com.yicj.sentinel.service;


import com.yicj.sentinel.SentinelApplication;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = SentinelApplication.class)
public class HelloServiceTest {

    @Resource
    private HelloService helloService ;

    @Test
    public void hello(){
        String retValue = helloService.hello("yicj");
        log.info("ret value : {}", retValue);
    }
}
