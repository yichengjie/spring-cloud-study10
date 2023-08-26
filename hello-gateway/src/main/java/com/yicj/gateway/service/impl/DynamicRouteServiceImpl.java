package com.yicj.gateway.service.impl;


import com.yicj.gateway.service.DynamicRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DynamicRouteServiceImpl
        implements DynamicRouteService, ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator ;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter ;

    private ApplicationEventPublisher eventPublisher ;

    /**
     * 新增路由
     * @param routeDefinition 路由定义
     * @return
     */
    @Override
    public Mono<Void> addRouteDefinition(RouteDefinition routeDefinition){
        return routeDefinitionWriter.save(Mono.just(routeDefinition)
                .then(Mono.fromRunnable(this::publishEvent)));
    }

    @Override
    public Mono<Void> updateRouteDefinition(RouteDefinition routeDefinition) {
        // 执行删除, 然后保存，之后发布刷新事件
        return routeDefinitionWriter.delete(Mono.just(routeDefinition).map(RouteDefinition::getId))
                .then(Mono.fromRunnable(() -> routeDefinitionWriter.save(Mono.just(routeDefinition))))
                .then(Mono.fromRunnable(this::publishEvent));
    }

    @Override
    public Mono<Void> addRouteDefinitionList(List<RouteDefinition> list) {
        //1. 先清空所有路由信息
        //2. 讲list中路由信息都添加进去
        return routeDefinitionLocator.getRouteDefinitions()
                .map(RouteDefinition::getId)
                .doOnNext(id -> routeDefinitionWriter.delete(Mono.just(id)))
                .thenMany(Flux.fromIterable(list))
                .doOnNext(item -> {
                    routeDefinitionWriter.save(Mono.just(item)) ;
                }).doOnComplete(this::publishEvent)
                .then()
        ;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher ;
    }


    private void publishEvent(){
        eventPublisher.publishEvent(new RefreshRoutesEvent(this)) ;
    }
}
