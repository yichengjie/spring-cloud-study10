package com.yicj.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author: yicj
 * @date: 2023/8/20 9:27
 */
@ConditionalOnProperty("nacos.gateway.route.config.data-id")
@Configuration
public class GatewayConfig {

    public static final long DEFAULT_TIMEOUT = 30000 ;

    public static String NACOS_SERVER_ADDRESS ;

    public static String NACOS_NAMESPACE ;

    public static String NACOS_ROUTE_DATA_ID ;

    public static String NACOS_ROUTE_GROUP ;

    @Value("${spring.cloud.nacos.config.server-addr}")
    public void setNacosServerAddress(String nacosServerAddress){
        NACOS_SERVER_ADDRESS = nacosServerAddress ;
    }


    @Value("${nacos.gateway.route.config.namespace}")
    public void setNacosNamespace(String nacosNamespace){
        NACOS_NAMESPACE = nacosNamespace ;
    }

    @Value("${nacos.gateway.route.config.group}")
    public void setNacosRouteGroup(String nacosRouteGroup){
        NACOS_ROUTE_GROUP = nacosRouteGroup ;
    }

    @Value("${nacos.gateway.route.config.data-id}")
    public void setNacosRouteDataId(String nacosRouteDataId){
        NACOS_ROUTE_DATA_ID = nacosRouteDataId ;
    }


}