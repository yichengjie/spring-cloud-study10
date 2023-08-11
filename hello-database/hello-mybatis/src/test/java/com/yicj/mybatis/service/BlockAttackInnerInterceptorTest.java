package com.yicj.mybatis.service;

import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * 防全表更新与删除插件
 * @author: yicj
 * @date: 2023/8/11 11:17
 */
@Slf4j
@Transactional
@SpringBootTest(classes = MybatisApplication.class)
public class BlockAttackInnerInterceptorTest {

    @Autowired
    private UserService userService ;


    @Test
    void blockAttack(){
        UserEntity user = new UserEntity() ;
        user.setId(1);
        user.setName("update name");
        userService.update(user, null) ;
    }

}
