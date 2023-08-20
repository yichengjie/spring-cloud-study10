package com.yicj.study.mvc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = WebMvcApplication.class)
public class WebMvcApplicationTest {

    @Test
    public void init(){

        log.info("success ..");
    }

}
