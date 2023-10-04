package com.yicj.study.aop.service;

import com.yicj.study.aop.config.HelloAopConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/**
 * @author yicj
 * @date 2023年10月04日 9:35
 */
@Slf4j
@SpringJUnitConfig(classes = HelloAopConfig.class)
public class HelloServiceTest {

    @Autowired
    private HelloService helloService ;

    @Test
    public void hello(){
        String retValue = helloService.hello("张三");
        log.info("ret value : {}", retValue);
    }
}
