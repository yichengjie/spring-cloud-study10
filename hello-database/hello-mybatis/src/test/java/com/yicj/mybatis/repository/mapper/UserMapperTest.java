package com.yicj.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 分页插件测试
 * @author: yicj
 * @date: 2023/8/11 9:03
 */
//@MybatisPlusTest
@Slf4j
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

    @Test
    void batchInsert(){
        IntStream.range(3, 22)
            .mapToObj(item -> {
                UserEntity entity = new UserEntity();
                entity.setId(item);
                entity.setName("王五[" + item + "]");
                entity.setJob("工作[" + item + "]");
                entity.setCompany("公司[" + item + "]");
                return entity;
            }).forEach(item -> userMapper.insert(item));
    }

    @Test
    void pageList(){
        //IPage<UserEntity> pageParam = new PageDTO<>(1,3) ;
        Page<UserEntity> pageParam = new Page<>(1,3) ;
        pageParam.setSearchCount(false) ;
        pageParam.optimizeCountSql() ;
        pageParam.setOptimizeJoinOfCountSql(true);
        IPage<UserEntity> page = userMapper.selectPage(pageParam, null);
        log.info("total records : {}", page.getTotal());
        log.info("page number : {}", page.getPages());
        page.getRecords().forEach(item -> log.info("item : {}", item));
    }


}
