package com.yicj.client.config;

import com.yicj.client.properties.CustomProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yicj
 * @date: 2023/6/17 11:19
 */
@Configuration
@EnableConfigurationProperties(CustomProperties.class)
public class AppConfig {

}
