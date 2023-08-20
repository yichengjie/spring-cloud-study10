package com.yicj.sentinel.feign;


import com.yicj.sentinel.SentinelClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = SentinelClientApplication.class)
public class NacosHelloClientTest {

    @Qualifier("nacosHelloClient")
    @Autowired
    private NacosHelloClient helloClient ;

    @Test
    public void hello(){
        String retValue = helloClient.hello();
        log.info("ret value : {}", retValue);
    }

    @Test
    public void exception(){
        String retValue = helloClient.exception();
        log.info("ret value : {}", retValue);
    }

}
