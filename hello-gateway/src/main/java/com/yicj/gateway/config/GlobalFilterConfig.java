package com.yicj.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Configuration
public class GlobalFilterConfig {

    @Bean
    public GlobalFilter customGlobalPreFilter(){
        return (exchange, chain) -> exchange.getPrincipal()
                .map(Principal::getName)
                .defaultIfEmpty("Default User")
                .map(userName -> {
                    // adds header to proxied request
                    ServerHttpRequest request = exchange.getRequest()
                            .mutate()
                            .header("CUSTOM-REQUEST-HEADER", userName)
                            .build();
                    return exchange.mutate().request(request).build();
                })
                .flatMap(chain::filter) ;
    }

    @Bean
    public GlobalFilter customGlobalPostFilter(){
        return (exchange, chain) -> chain.filter(exchange)
                .then(Mono.just(exchange))
                .map(serverWebExchange ->{
                    // adds header to response
                    String value = HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ?"It worked": "It did not work" ;
                    serverWebExchange.getResponse().getHeaders().set("CUSTOM-RESPONSE-HEADER",value) ;
                    return serverWebExchange ;
                }).then() ;
    }

}
