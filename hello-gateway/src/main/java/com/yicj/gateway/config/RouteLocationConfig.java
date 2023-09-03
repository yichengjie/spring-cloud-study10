package com.yicj.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

/**
 * @author yicj
 * @date 2023年08月26日 14:29
 */
//@Configuration
public class RouteLocationConfig {

//    @Bean
//    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(p -> p
//                        .path("/get")
//                        .filters(f -> f.addRequestHeader("Hello", "World"))
//                        .uri("http://httpbin.org:80"))
//                .route(p -> p
//                        .host("*.circuitbreaker.com")
//                        .filters(f -> f
//                                .circuitBreaker(config -> config
//                                        .setName("mycmd")
//                                        .setFallbackUri("forward:/fallback/index")
//                                ))
//                        .uri("http://httpbin.org:80"))
////                .route(p -> p
////                        .path("/fallback")
////                        .filters(f -> f.stripPrefix(1))
////                        .uri("http://localhost:8080/fallback"))
//                .build();
//    }
}
