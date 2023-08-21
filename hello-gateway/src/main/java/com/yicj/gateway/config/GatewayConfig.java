package com.yicj.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yicj
 * @date: 2023/8/20 9:27
 */

@Configuration
public class GatewayConfig {


    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .host("*.circuitbreaker.com")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("mycmd")
                                        .setFallbackUri("forward:/fallback/index")
                                ))
                        .uri("http://httpbin.org:80"))
//                .route(p -> p
//                        .path("/fallback")
//                        .filters(f -> f.stripPrefix(1))
//                        .uri("http://localhost:8080/fallback"))
                .build();
    }

}