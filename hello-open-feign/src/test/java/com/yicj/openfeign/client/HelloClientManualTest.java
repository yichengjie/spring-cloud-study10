package com.yicj.openfeign.client;

import com.yicj.openfeign.OpenFeignApplication;
import com.yicj.openfeign.config.HelloClientManualConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
@SpringBootTest(classes = OpenFeignApplication.class)
public class HelloClientManualTest implements ApplicationContextAware, InitializingBean {

    private HelloClientManual clientManual ;

    private ApplicationContext applicationContext ;

    @Test
    public void index(){
        String retValue = clientManual.index();
        log.info("ret value : {}", retValue);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext ;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext() ;
        context.setParent(applicationContext);
        context.register(HelloClientManualConfiguration.class);
        context.refresh();
        HelloClientManualConfiguration manualConfiguration = context.getBean(HelloClientManualConfiguration.class);
        clientManual = manualConfiguration.buildHelloClientManual();
    }
}
