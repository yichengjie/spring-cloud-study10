package com.yicj.gateway.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.yicj.gateway.config.GatewayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;


@Slf4j
@Service
@ConditionalOnProperty("nacos.gateway.route.config.data-id")
@DependsOn("gatewayConfig")
public class NacosDynamicRouteService {

    @Autowired
    private DynamicRouteService dynamicRouteService ;

    @PostConstruct
    private void init() throws NacosException {
        String serverAddr = GatewayConfig.NACOS_SERVER_ADDRESS;
        //
        String namespace = GatewayConfig.NACOS_NAMESPACE;
        String group = GatewayConfig.NACOS_ROUTE_GROUP;
        String dataId = GatewayConfig.NACOS_ROUTE_DATA_ID;
        Properties properties = new Properties();
        properties.put("serverAddr", serverAddr);
        properties.put("namespace", namespace);
        //
        ConfigService configService = NacosFactory.createConfigService(properties);
        String content = configService.getConfig(dataId, group, 5000);
        log.info("config content : {}", content);
        // 获取全部配置，并添加到路由配置中
        List<RouteDefinition> list = JSON.parseArray(content, RouteDefinition.class);
        list.forEach(item -> dynamicRouteService.addRouteDefinition(item).subscribe());

        configService.addListener(dataId, group, new AbstractListener() {
            @Override
            public void receiveConfigInfo(String configInfo) {
                log.info("接收到配置变化通知：{}", configInfo);
                List<RouteDefinition> list = JSON.parseArray(content, RouteDefinition.class);
                dynamicRouteService.addRouteDefinitionList(list).subscribe() ;
            }
        });
    }

}
