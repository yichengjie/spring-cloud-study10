package com.yicj.resission.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yicj
 * @date: 2023/9/24 14:47
 */
@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config() ;
        config.useSingleServer()
                .setAddress("redis://192.168.99.51:6379") ;
        return Redisson.create(config) ;
    }

}
