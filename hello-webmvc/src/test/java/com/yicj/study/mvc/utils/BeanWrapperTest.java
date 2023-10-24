package com.yicj.study.mvc.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

import java.time.LocalDateTime;

/**
 * @author yicj
 * @date 2023/10/24 20:35
 */
@Slf4j
public class BeanWrapperTest {

    @Test
    public void setProperty(){
        HelloBean bean = new HelloBean() ;
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean) ;
        beanWrapper.setPropertyValue("name", "张三");
        beanWrapper.setPropertyValue("createTime", LocalDateTime.now());
        // 这里属性不存在，会直接报错
        //beanWrapper.setPropertyValue("createBy", "张三");
        log.info("createBy has writable property : {}",beanWrapper.isWritableProperty("name"));
        log.info("createTime has writable property : {}",beanWrapper.isWritableProperty("createTime"));
        log.info("createBy has writable property : {}",beanWrapper.isWritableProperty("createBy"));
        log.info("bean : {}", bean);
    }


    @Test
    public void setProperty2(){
        HelloBean bean = new HelloBean() ;
        BeanWrapper beanWrapper = new BeanWrapperImpl(bean) ;
        PropertyValue namePropertyValue = new PropertyValue("name", "张三") ;
        namePropertyValue.setOptional(true);
        beanWrapper.setPropertyValue(namePropertyValue);
        //
        // 这里数据类型不一致会报错, 所以数据类型一定要保持一致
        //PropertyValue createTimePropertyValue = new PropertyValue("createTime", "测试") ;
        //createTimePropertyValue.setOptional(true);
        //beanWrapper.setPropertyValue(createTimePropertyValue);
        //
        PropertyValue createByPropertyValue = new PropertyValue("createBy", "张三") ;
        createByPropertyValue.setOptional(true);
        beanWrapper.setPropertyValue(createByPropertyValue);

        log.info("bean : {}", bean);
    }

    @Data
    static class HelloBean{
        private String name ;

        private String code ;

        private LocalDateTime createTime ;
    }
}
