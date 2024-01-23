package com.yicj.study.ioc;

import com.yicj.study.ioc.registry.HelloServiceRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(HelloServiceRegistry.class)
@SpringBootApplication
public class HelloIocApplication {

    public static void main(String[] args) {

        SpringApplication.run(HelloIocApplication.class, args) ;
    }
}
