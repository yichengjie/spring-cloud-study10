package com.yicj.sentinel.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

//    @Bean
//    public FilterRegistrationBean sentinelFilterRegistration() {
//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new CommonFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("sentinelFilter");
//        registration.setOrder(1);
//
//        return registration;
//    }
}