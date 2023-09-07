package com.yicj.webflux.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author yicj
 * @date 2023年08月26日 21:44
 */
@Configuration
public class WebClientConfig {

    @Bean
    public ReactorResourceFactory resourceFactory() {
        return new ReactorResourceFactory();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder builder(){
        return WebClient.builder().filter((request, next) -> {
            ClientRequest newRequest =  ClientRequest.from(request)
                    .header("x-token", "bar")
                    .build();
            return next.exchange(newRequest);
        });
    }

    @Bean
    public WebClient webClient(){
        Function<HttpClient, HttpClient> mapper = client -> {
            // Further customizations...
             client.doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(10))
                .addHandlerLast(new WriteTimeoutHandler(10))
             )
             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
             .responseTimeout(Duration.ofSeconds(2));
             ;
            return client ;
        };
        ClientHttpConnector connector =
                new ReactorClientHttpConnector(resourceFactory(), mapper);
        //
        return WebClient.builder()
                .clientConnector(connector).build();
    }
}
