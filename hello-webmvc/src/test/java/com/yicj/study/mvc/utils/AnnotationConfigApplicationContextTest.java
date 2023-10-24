package com.yicj.study.mvc.utils;

import com.yicj.study.mvc.model.form.HelloIndexForm;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yicj
 * @date 2023/10/24 21:04
 */
@Slf4j
public class AnnotationConfigApplicationContextTest {

    @Test
    public void hello(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext() ;
        context.register(AppConfig.class);
        context.refresh();
        //
        HelloIndexForm bean = context.getBean(HelloIndexForm.class);
        log.info("bean : {}", bean);
    }


    @Configuration
    static class AppConfig{

        @Bean
        public HelloIndexForm indexForm(){
            return new HelloIndexForm() ;
        }
    }

}
