package com.yicj.mybatis.plugin;

import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 数据变化日志打印
 * @author: yicj
 * @date: 2023/8/11 14:25
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class DataChangeRecorderInnerInterceptorTest {

    @Autowired
    private UserService userService ;

    @Test
    void testOptLocker4WrapperIsNull() {
        UserEntity userInsert = new UserEntity();
        userInsert.setName("optLockerTest");
        userInsert.setJob("test") ;
        userInsert.setCompany("test") ;
        userService.save(userInsert);
    }
}
