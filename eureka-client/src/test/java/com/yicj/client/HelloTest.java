package com.yicj.client;

import com.yicj.client.properties.CustomProperties;
import com.yicj.client.properties.UserProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: yicj
 * @date: 2023/6/17 11:14
 */
@Slf4j
@SpringBootTest
public class HelloTest {

    @Autowired
    private CustomProperties customProperties ;

    @Test
    public void hello(){
        UserProperties user = customProperties.getUser();
        log.info("user info: {}", user);
    }
}
