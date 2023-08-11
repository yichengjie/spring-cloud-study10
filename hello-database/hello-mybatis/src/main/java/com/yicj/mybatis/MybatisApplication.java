package com.yicj.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author: yicj
 * @date: 2023/8/11 8:48
 */
@EnableAspectJAutoProxy
@MapperScan("com.yicj.mybatis.repository.mapper")
@SpringBootApplication
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args) ;
    }
}
