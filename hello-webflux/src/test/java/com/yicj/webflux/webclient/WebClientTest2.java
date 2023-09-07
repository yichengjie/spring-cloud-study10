package com.yicj.webflux.webclient;

import com.yicj.webflux.HelloWebFluxApplication;
import com.yicj.webflux.remote.client.UserHelloClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author yicj
 * @date 2023年08月26日 21:54
 */
@Slf4j
@SpringBootTest(classes = HelloWebFluxApplication.class)
public class WebClientTest2 {

    @Autowired
    private WebClient.Builder builder ;

    @Test
    public void retrieve() throws Exception{
        String value = builder
                .baseUrl("http://hello-user-server/user-service")
                .build()
                .get()
                .uri("/hello/index")
                //.uri("http://localhost:8085/user-service/hello/index")
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("value : {}", value);
    }

    @Test
    public void index() throws InterruptedException {
        WebClient webClient = builder.baseUrl("http://hello-user-server/user-service")
                .build();
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder(WebClientAdapter.forClient(webClient))
                .build();
        UserHelloClient helloClient = factory.createClient(UserHelloClient.class);
        String value = helloClient.index();
        log.info("value : {}",value);
    }

}
