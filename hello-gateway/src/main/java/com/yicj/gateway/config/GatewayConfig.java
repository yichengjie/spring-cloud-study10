package com.yicj.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
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

    public static final long default_timeout = 30000 ;

    private static String NACOS_SERVER_ADDRESS ;

    private static String NACOS_NAMESPACE ;

    private static String NACOS_ROUTE_DATA_ID ;

    private static String NACOS_ROUTE_GROUP ;

    @Value("${spring.cloud.nacos.config.server-addr}")
    public void setNacosServerAddress(String nacosServerAddress){
        NACOS_SERVER_ADDRESS = nacosServerAddress ;
    }


    @Value("${spring.cloud.nacos.config.server-addr}")
    public void setNacosNamespace(String nacosNamespace){
        NACOS_NAMESPACE = nacosNamespace ;
    }

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