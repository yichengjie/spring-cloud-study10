package com.yicj.webflux.config;

import com.yicj.webflux.handler.HelloHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

/**
 * @author yicj
 * @date 2023年08月26日 17:16
 */
@Configuration
public class RoutesConfig {

    @Autowired
    private HelloHandler helloHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
         RouterFunction<ServerResponse> route = RouterFunctions.route()
                .GET("/person/{id}", accept(TEXT_PLAIN), helloHandler::findById)
                .GET("/person", accept(TEXT_PLAIN), helloHandler::listPeople)
                .POST("/person", accept(APPLICATION_JSON),helloHandler::createPerson)
                .build();
         return route ;
    }

}
