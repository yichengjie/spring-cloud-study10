package com.yicj.gateway.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Service;

@Service
public class DynamicRouteServiceImpl {

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator ;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter ;


}
