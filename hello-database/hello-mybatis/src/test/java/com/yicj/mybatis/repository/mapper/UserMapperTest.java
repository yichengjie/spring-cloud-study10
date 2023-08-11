package com.yicj.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 9:03
 */
@Slf4j
//@MybatisPlusTest
@SpringBootTest(classes = MybatisApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    void listAll(){
        List<UserEntity> list = userMapper.selectList(new QueryWrapper<>());
        log.info("list size : {}", list.size());
        list.forEach(item -> log.info("item : {}", item));
    }
}
