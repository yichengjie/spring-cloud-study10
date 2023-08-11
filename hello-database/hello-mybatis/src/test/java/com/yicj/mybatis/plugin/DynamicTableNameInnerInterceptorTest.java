package com.yicj.mybatis.plugin;

import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.UserMapper;
import com.yicj.mybatis.support.RequestDataHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashMap;

/**
 * @author: yicj
 * @date: 2023/8/11 12:48
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class DynamicTableNameInnerInterceptorTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        RequestDataHelper.setRequestData(new HashMap<String, Object>() {{
            put("id", 123);
            put("hello", "tomcat");
            put("name", "汤姆凯特");
        }});
        // 自己去观察打印 SQL 目前随机访问 user_2018  user_2019 表
        for (int i = 0; i < 6; i++) {
            UserEntity user = userMapper.selectById(1);
            System.err.println(user.getName());
        }
    }

}
