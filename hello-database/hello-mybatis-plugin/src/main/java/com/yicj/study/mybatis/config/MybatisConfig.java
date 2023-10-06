package com.yicj.study.mybatis.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.yicj.study.mybatis.plugins.PaginationInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yicj
 * @date 2023/10/6 21:31
 */
@Configuration
public class MybatisConfig {
//
//    @Bean
//    public MybatisPlusInterceptor mybatisPagePlusInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        //分页插件
//        //interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        Interceptor paginationInterceptor = new PaginationInterceptor();
//        //interceptor.addInnerInterceptor();
//        return interceptor;
//    }

    @Bean
    public Interceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
