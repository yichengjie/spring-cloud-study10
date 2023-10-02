package com.yicj.study.converter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

/**
 * @author: yicj
 * @date: 2023/9/29 15:44
 */
@Configuration
public class ConverterSkillConfig {
    @Bean
    public ConversionServiceFactoryBean conversionService(){
        return new ConversionServiceFactoryBean() ;
    }
}
