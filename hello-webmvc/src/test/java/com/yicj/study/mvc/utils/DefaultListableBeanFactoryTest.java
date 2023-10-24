package com.yicj.study.mvc.utils;

import com.yicj.study.mvc.model.form.HelloIndexForm;
import com.yicj.study.mvc.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;

/**
 * @author yicj
 * @date 2023/10/24 21:51
 */
@Slf4j
public class DefaultListableBeanFactoryTest {

    @Test
    public void hello(){
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory() ;
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory) ;
        ClassPathResource resource = new ClassPathResource("spring/hello-bean.xml") ;
        beanDefinitionReader.loadBeanDefinitions(resource) ;
        HelloService helloService = beanFactory.getBean("helloService", HelloService.class);
        String retValue = helloService.hello("张三");
        log.info("ret value : {}", retValue);
    }
}
