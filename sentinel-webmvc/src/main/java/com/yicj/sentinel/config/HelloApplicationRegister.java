package com.yicj.sentinel.config;

import com.yicj.sentinel.service.HelloService;
import com.yicj.sentinel.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloApplicationRegister implements ApplicationContextAware, EnvironmentAware, ApplicationRunner {

    private Environment env ;

    private ApplicationContext ctx ;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext ;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment ;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GenericApplicationContext context = (GenericApplicationContext) ctx ;
        String port = env.getProperty("server.port");
        log.info("----------> server port {}", port);
        context.registerBean(HelloService.class, () -> {
            HelloService service = new HelloServiceImpl() ;
            service.setAddress("BJS");
            return service ;
        });
    }
}
