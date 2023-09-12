package com.yicj.sentinel.utils;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Properties;

@Slf4j
public class ConfigServiceTest {

    private long DEFAULT_TIMEOUT = 3000 ;

    @Test
    public void hello() throws NacosException {
        Properties properties = new Properties() ;
        properties.setProperty("serverAddr", "127.0.0.1:8848") ;
        //properties.setProperty("namespace", "public") ;
        //properties.setProperty("namespace", "PUBLIC") ;
        ConfigService configService = NacosFactory.createConfigService(properties);
        String groupId = "DEFAULT_GROUP" ;
        String dataId = "sentinel-client-app-sentinel.json" ;
        String content = configService.getConfig(dataId, groupId, DEFAULT_TIMEOUT);
        log.info("config content : {}", content);
    }
}
