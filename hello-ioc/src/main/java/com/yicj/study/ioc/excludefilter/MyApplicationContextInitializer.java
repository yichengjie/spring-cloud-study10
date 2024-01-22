package com.yicj.study.ioc.excludefilter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.PriorityOrdered;

/**
 * @author yicj
 * @date 2023/11/5 11:49
 */
public class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.addBeanFactoryPostProcessor(new TypeExcludeFilterRegistry());
    }
    private static class TypeExcludeFilterRegistry
            implements PriorityOrdered, BeanDefinitionRegistryPostProcessor{
        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            registry.registerBeanDefinition(
                    MyTypeExcludeFilter.class.getName(), new RootBeanDefinition(MyTypeExcludeFilter.class));
        }
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        }
        @Override
        public int getOrder() {
            // 最高优先级
            return HIGHEST_PRECEDENCE;
        }
    }
}
