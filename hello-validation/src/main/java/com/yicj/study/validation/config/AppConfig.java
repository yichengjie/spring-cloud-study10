package com.yicj.study.validation.config;

import com.yicj.study.validation.model.UserVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: yicj
 * @date: 2023/9/29 13:56
 */
@Configuration
public class AppConfig {
    @Bean
    public UserVO userVo(){
        return new UserVO("test", 16) ;
    }
}
