package com.yicj.study.ioc.configuration;

import com.yicj.study.ioc.configuration.model.Pet;
import com.yicj.study.ioc.configuration.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.util.LambdaSafe;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.WebServerFactoryCustomizerBeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;
import java.util.List;

/**
 * @author yicj
 * @date 2023/11/5 11:34
 */
@Slf4j
public class AppConfigTest {

    @Test
    public void hello(){
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        //
        User tom01 = ctx.getBean("user", User.class);
        User tom02 = ctx.getBean("user", User.class);
        Pet pet = ctx.getBean("pet", Pet.class);
        //true 可以证明向容器中注入的组件是单实例的
        log.info("组件：" + (tom01 == tom02));
        log.info("用户的宠物：" + (tom01.getPet() == pet));
    }

    @Test
    public void customize(){
        HelloFactory helloFactory = new HelloFactory() ;
        //
        LambdaSafe.callbacks(Customizer.class, getCustomizers(), helloFactory)
                .withLogger(WebServerFactoryCustomizerBeanPostProcessor.class)
                .invoke((customizer) -> customizer.customize(helloFactory));
    }


    private List<Customizer> getCustomizers(){
        return List.of(new Customizer(){

            @Override
            public void customize(HelloFactory factory) {
                log.info("customize execute... ");
            }
        }) ;
    }


    interface Customizer{
        void customize(HelloFactory factory) ;
    }

    class HelloFactory{

    }
}
