package com.yicj.gateway.service;

import org.springframework.cloud.gateway.route.RouteDefinition;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DynamicRouteService {

    Mono<Void> addRouteDefinition(RouteDefinition routeDefinition) ;

    Mono<Void> updateRouteDefinition(RouteDefinition routeDefinition) ;

    Mono<Void> addRouteDefinitionList(List<RouteDefinition> list) ;
}
