package com.yicj.sentinel.config;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.yicj.sentinel.utils.GlobalExceptionUtil;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author yicj
 * @date 2023年09月12日 15:06
 */
@Configuration
public class SentinelConfig {

    @Bean
    @LoadBalanced
    @SentinelRestTemplate(
            blockHandler = "handleBlock", blockHandlerClass = GlobalExceptionUtil.class,
            fallback = "fallback", fallbackClass = GlobalExceptionUtil.class
    )
    public RestTemplate restTemplate(){

        return new RestTemplate() ;
    }

}
