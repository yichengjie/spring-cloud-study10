package com.yicj.mybatis.plugin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.StudentEntity;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.StudentMapper;
import com.yicj.mybatis.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 租户字段
 * @author: yicj
 * @date: 2023/8/11 14:55
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class TenantLineInnerInterceptorTest {

    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private StudentMapper studentMapper ;

    @Test
    public void listAllUser(){
        List<UserEntity> list = userMapper.selectList(new QueryWrapper<>());
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }

    @Test
    public void listAllStudent(){
        List<StudentEntity> list = studentMapper.selectList(new QueryWrapper<>());
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }

}
