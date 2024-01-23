package com.yicj.study.ioc.configuration;

import com.yicj.study.ioc.configuration.model.Pet;
import com.yicj.study.ioc.configuration.model.User;
import com.yicj.study.ioc.repository.HelloRepository;
import com.yicj.study.ioc.service.HelloService;
import com.yicj.study.ioc.service.impl.HelloServiceImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author yicj
 * @date 2023/11/5 11:32
 */
@Configuration/*(proxyBeanMethods = false)*/
//@Component
public class AppConfig {
    @Bean
    public User user() {
        User user = new User();
        user.setUsername("张三");
        //user组件依赖了Pet组件
        user.setPet(pet());
        return user;
    }

    @Bean
    public Pet pet() {
        Pet pet = new Pet();
        pet.setName("阿猫");
        return pet;
    }



    @Bean("helloService2")
    @ConditionalOnMissingBean(name = "helloService2")
    HelloService helloService(ObjectProvider<HelloRepository> helloRepositoryProvider) {
        HelloRepository helloRepository = helloRepositoryProvider.getIfAvailable();
        HelloServiceImpl helloService = new HelloServiceImpl("AppConfig HelloService");
        helloService.setRepository(helloRepository);
        return helloService;
    }
}
