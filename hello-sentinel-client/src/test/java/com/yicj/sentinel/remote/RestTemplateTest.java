package com.yicj.sentinel.remote;

import com.yicj.sentinel.BaseJunitTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author yicj
 * @date 2023年09月12日 15:32
 */
@Slf4j
public class RestTemplateTest extends BaseJunitTest {

    @Autowired
    private RestTemplate restTemplate ;

    @Test
    public void hello(){
        URI uri = UriComponentsBuilder.fromHttpUrl("http://nacos-client-app/hello/index")
                .build()
                .toUri();
        HttpMethod httpMethod = HttpMethod.GET;
        RequestEntity<String> requestEntity = new RequestEntity<>(httpMethod, uri);
        String retValue = restTemplate.exchange(requestEntity, String.class).getBody();
        log.info("ret value : {}", retValue);
    }

    @Test
    public void exception(){
        String retValue = restTemplate.getForObject("http://nacos-client-app/hello/exception", String.class);
        log.info("ret value : {}", retValue);
    }
}
