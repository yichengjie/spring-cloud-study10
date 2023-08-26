package com.yicj.gateway.service.impl;


import com.yicj.gateway.service.DynamicRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;

@Slf4j
@Service
public class DynamicRouteServiceImpl
        implements DynamicRouteService, ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator ;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter ;

    private ApplicationEventPublisher publisher ;

    /**
     * 新增路由
     * @param routeDefinition 路由定义
     * @return
     */
    @Override
    public Mono<Void> addRouteDefinition(RouteDefinition routeDefinition){
        return routeDefinitionWriter.save(Mono.just(routeDefinition))
                .then(Mono.fromRunnable(this::publishEvent));
    }

    @Override
    public Mono<Void> updateRouteDefinition(RouteDefinition routeDefinition) {
        // 执行删除, 然后保存，之后发布刷新事件
        return routeDefinitionWriter.delete(Mono.just(routeDefinition).map(RouteDefinition::getId))
                .then(routeDefinitionWriter.save(Mono.just(routeDefinition)))
                .then(Mono.fromRunnable(this::publishEvent));
    }

    @Override
    public Mono<Void> addRouteDefinitionList(List<RouteDefinition> list) {
        //1. 先清空所有路由信息
        //2. 讲list中路由信息都添加进去
        return routeDefinitionLocator.getRouteDefinitions()
                .map(RouteDefinition::getId)
                .flatMap(id -> routeDefinitionWriter.delete(Mono.just(id)))
                .thenMany(Flux.fromIterable(list))
                .flatMap(item -> routeDefinitionWriter.save(Mono.just(item)))
                .then(Mono.fromRunnable(this::publishEvent));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher ;
    }


    private void publishEvent(){
        log.info("发布更新路由事件 !!!!");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }
}
