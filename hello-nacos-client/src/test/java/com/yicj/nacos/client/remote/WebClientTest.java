package com.yicj.nacos.client.remote;

import com.yicj.nacos.client.NacosClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author yicj
 * @date 2023年08月27日 15:17
 */
@Slf4j
@SpringBootTest(classes = NacosClientApplication.class)
public class WebClientTest {

//    @Autowired
//    private WebClient webClient ;
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

}
