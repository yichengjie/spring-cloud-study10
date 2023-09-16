package com.yicj.webflux.webclient;

import com.yicj.common.model.form.SavePersonForm;
import com.yicj.webflux.HelloWebFluxApplication;
import com.yicj.webflux.remote.client.UserHelloClient;
import com.yicj.webflux.repository.entity.PersonEntity;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientRequest;

import java.time.Duration;


/**
 * @author yicj
 * @date 2023年08月26日 21:14
 */
@Slf4j
public class WebClientTest {

    // 注意accept中的值与服务器端定义的值保持一直，否则会出现404
    @Test
    public void queryById(){
        String id = "0a990d690f39465384017a25d61fdec4" ;
        WebClient client = WebClient.create("http://localhost:8084");
        PersonEntity entity = client.get()
                .uri("/person/{id}", id)
                .accept(MediaType.TEXT_PLAIN)
                //.exchange()
                .retrieve()
                .bodyToMono(PersonEntity.class)
                .block();
        log.info("entity : {}", entity);
    }

    @Test
    public void listAll() throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8084");
        client.get()
                .uri("/person")
                .accept(MediaType.TEXT_PLAIN)
                .retrieve()
                .bodyToFlux(PersonEntity.class)
                .subscribe(entity -> log.info("id: {}, username: {}, address: {}", entity.getId(), entity.getUsername(), entity.getAddress()));
        Thread.sleep(2000);
    }


    @Test
    public void ex() throws InterruptedException {
        WebClient client = WebClient.create("http://localhost:8084");
        client.get()
                .uri("/person")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .subscribe(item -> log.info("item {}", item));
        Thread.sleep(2000);
    }

    @Test
    public void save(){
        Mono<SavePersonForm> savePersonForm = Mono.fromSupplier(() -> {
            SavePersonForm form = new SavePersonForm();
            form.setUsername("张三");
            form.setAddress("北京");
            return form;
        });
        WebClient client = WebClient.create("http://localhost:8084");
        PersonEntity entity = client.post()
                .uri("/person")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(savePersonForm, SavePersonForm.class)
                .retrieve()
                .bodyToMono(PersonEntity.class)
                .block();
        log.info("entity : {}", entity);
    }

    @Test
    public void hello() throws InterruptedException {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofSeconds(2))
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10))
                        .addHandlerLast(new WriteTimeoutHandler(10))
                );
        WebClient client = WebClient.builder()
                .codecs(config -> config.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        //
        client.get()
                .uri("https://example.org")
                .httpRequest(request -> {
                    HttpClientRequest reactorRequest = request.getNativeRequest() ;
                    reactorRequest.responseTimeout(Duration.ofSeconds(2)) ;
                })
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(value -> log.info("value -> {}", value));
        Thread.sleep(2000);
    }
}
