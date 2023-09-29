package com.yicj.study.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: yicj
 * @date: 2023/9/29 15:40
 */
@EnableAsync
@SpringBootApplication
public class ConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args) ;
    }
}
