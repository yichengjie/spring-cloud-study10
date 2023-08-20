package com.yicj.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class PreGatewayFilterFactory  extends AbstractGatewayFilterFactory<PreGatewayFilterFactory.Config> {


    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            // If you want to build a "pre" filter you need to manipulate the
            // request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            // use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build()) ;
        };
    }


    class PreGatewayFilter implements GatewayFilter{

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return null;
        }
    }

    public static class Config{

    }

}
