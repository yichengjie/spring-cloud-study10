package com.yicj.gateway.predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Config config) {
        // grab configuration from Config object
        return exchange ->{
            // grab the request
            ServerHttpRequest request = exchange.getRequest();
            // take information form the request to see if it matches configuration
            return matches(config, request);
        } ;
    }

    private boolean matches(Config config, ServerHttpRequest request){

        return true ;
    }

    public static class Config{

    }
}
