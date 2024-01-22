package com.yicj.study.ioc.registry;

import com.yicj.study.ioc.repository.impl.MysqlHelloRepository;
import com.yicj.study.ioc.repository.impl.RedisHelloRepository;
import com.yicj.study.ioc.service.impl.HelloServiceImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;


@Component
public class HelloServiceRegistry implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AbstractBeanDefinition redisRepository =
                BeanDefinitionBuilder.genericBeanDefinition(RedisHelloRepository.class)
                .getBeanDefinition();
        registry.registerBeanDefinition("redisRepository", redisRepository);
        //
        AbstractBeanDefinition mysqlRepository =
                BeanDefinitionBuilder.genericBeanDefinition(MysqlHelloRepository.class)
                .getBeanDefinition();
        registry.registerBeanDefinition("mysqlRepository", mysqlRepository);
        //
        AbstractBeanDefinition helloService =
                BeanDefinitionBuilder.genericBeanDefinition(HelloServiceImpl.class)
                //.addConstructorArgReference("redisRepository")
                .addPropertyReference("repository", "mysqlRepository")
                .getBeanDefinition();
        registry.registerBeanDefinition("helloService", helloService);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
