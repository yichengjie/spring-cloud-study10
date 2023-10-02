package com.yicj.study.validation.config;

import com.yicj.study.validation.service.UserService;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * @author: yicj
 * @date: 2023/9/29 14:14
 */
@Configuration
public class ValidationSkill {

    /**
     *  bean 初始化验证是否符合JSR-303规范
     */
    @Bean
    public BeanPostProcessor beanValidationPostProcessor(){
        return new BeanValidationPostProcessor() ;
    }

    /**
     * 方法执行时验证参数是否符合 JSR-303规范
     */
    @Bean
    public BeanPostProcessor methodValidationPostProcessor(){
        return new MethodValidationPostProcessor() ;
    }

    @Bean
    public UserService userService(){
        return new UserService() ;
    }
}
