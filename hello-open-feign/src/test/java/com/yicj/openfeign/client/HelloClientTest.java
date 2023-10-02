package com.yicj.openfeign.client;

import com.yicj.openfeign.OpenFeignApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = OpenFeignApplication.class)
public class HelloClientTest {

    @Autowired
    private HelloClient helloClient ;

    @Test
    public void index(){
        String retValue = helloClient.index();
        log.info("ret value : {}", retValue);
    }
}
