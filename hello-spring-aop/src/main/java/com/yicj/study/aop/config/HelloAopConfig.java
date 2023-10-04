package com.yicj.study.aop.config;

import com.yicj.study.aop.aspect.HelloAspect;
import com.yicj.study.aop.service.HelloService;
import com.yicj.study.aop.service.impl.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author yicj
 * @date 2023年10月04日 9:21
 */
@Configuration
@EnableAspectJAutoProxy
public class HelloAopConfig {

    @Bean
    public HelloService helloService(){
        return new HelloServiceImpl() ;
    }

    @Bean
    public HelloAspect helloAspect(){
        return new HelloAspect() ;
    }

}
