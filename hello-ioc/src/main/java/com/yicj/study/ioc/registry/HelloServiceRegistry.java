package com.yicj.study.ioc.registry;

import com.yicj.study.ioc.repository.impl.MysqlHelloRepository;
import com.yicj.study.ioc.repository.impl.RedisHelloRepository;
import com.yicj.study.ioc.service.impl.HelloServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

public class HelloServiceRegistry implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.postProcessBeanDefinitionRegistry(registry);
    }

    private void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AbstractBeanDefinition redisRepository =
                BeanDefinitionBuilder.genericBeanDefinition(RedisHelloRepository.class)
                .getBeanDefinition();
        registry.registerBeanDefinition("redisRepository", redisRepository);
        //
        AbstractBeanDefinition mysqlRepository =
                BeanDefinitionBuilder.genericBeanDefinition(MysqlHelloRepository.class)
                .setPrimary(true)
                .getBeanDefinition();
        registry.registerBeanDefinition("mysqlRepository", mysqlRepository);
        //
        AbstractBeanDefinition helloService =
                BeanDefinitionBuilder.genericBeanDefinition(HelloServiceImpl.class)
                .addConstructorArgValue("BeanDefinitionRegistry HelloService")
                .addPropertyReference("repository", "mysqlRepository")
                .getBeanDefinition();
        registry.registerBeanDefinition("helloService2", helloService);
    }

}
