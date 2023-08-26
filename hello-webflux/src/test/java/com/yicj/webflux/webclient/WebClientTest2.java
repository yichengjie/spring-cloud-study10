package com.yicj.webflux.webclient;

import com.yicj.webflux.HelloWebFluxApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author yicj
 * @date 2023年08月26日 21:54
 */
@Slf4j
@SpringBootTest(classes = HelloWebFluxApplication.class)
public class WebClientTest2 {

    @Autowired
    private WebClient webClient ;

}
