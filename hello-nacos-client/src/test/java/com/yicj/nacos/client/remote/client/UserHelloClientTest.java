package com.yicj.nacos.client.remote.client;

import com.yicj.nacos.client.NacosClientApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月27日 18:58
 */
@Slf4j
@SpringBootTest(classes = NacosClientApplication.class)
public class UserHelloClientTest {

    @Autowired
    private WebClient.Builder builder ;

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
