package com.yicj.sentinel.service;


import com.yicj.sentinel.SentinelApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;

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
