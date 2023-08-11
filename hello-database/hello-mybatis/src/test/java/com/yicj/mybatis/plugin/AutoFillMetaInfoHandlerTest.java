package com.yicj.mybatis.plugin;

import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 自动填充字段
 * @author: yicj
 * @date: 2023/8/11 17:23
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class AutoFillMetaInfoHandlerTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    public void insert(){
        UserEntity entity = new UserEntity() ;
        entity.setId(30) ;
        entity.setName("赵六") ;
        entity.setCompany("test") ;
        entity.setJob("job-test") ;
        userMapper.insert(entity) ;
    }

    @Test
    public void update(){
        UserEntity entity = new UserEntity() ;
        entity.setId(3) ;
        entity.setName("王五3") ;
        entity.setCompany("test") ;
        entity.setJob("job-test") ;
        userMapper.updateById(entity) ;
    }

}
