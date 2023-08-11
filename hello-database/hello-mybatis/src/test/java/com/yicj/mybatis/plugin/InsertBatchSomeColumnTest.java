package com.yicj.mybatis.plugin;

import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.UserEntity;
import com.yicj.mybatis.repository.mapper.UserMapper;
import com.yicj.mybatis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: yicj
 * @date: 2023/8/11 16:21
 */
@Slf4j
//@Transactional
@SpringBootTest(classes = MybatisApplication.class)
public class InsertBatchSomeColumnTest {

    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private UserService userService ;

    @Test
    void batchInsertMapper(){
        List<UserEntity> list = IntStream.range(10000, 100000)
                .mapToObj(item -> {
                    UserEntity entity = new UserEntity();
                    entity.setId(item);
                    entity.setName("王五[" + item + "]");
                    entity.setJob("工作[" + item + "]");
                    entity.setCompany("公司[" + item + "]");
                    return entity;
                }).collect(Collectors.toList());
        StopWatch watch = new StopWatch() ;
        watch.start("init");
        Flux.fromIterable(list)
                .buffer(1000)
                .subscribe(fixList -> {
                    //userMapper.insertBatchSomeColumn(tmpList) ;
                    log.info("list size : {}", fixList.size());
                    userMapper.insertBatchSomeColumn(fixList) ;
                });
        watch.stop();
        log.info("=====> take time : {}", watch.getTotalTimeMillis());
    }


    @Test
    void batchInsertService(){
        List<UserEntity> list = IntStream.range(30, 1000)
                .mapToObj(item -> {
                    UserEntity entity = new UserEntity();
                    entity.setId(item);
                    entity.setName("王五[" + item + "]");
                    entity.setJob("工作[" + item + "]");
                    entity.setCompany("公司[" + item + "]");
                    return entity;
                }).collect(Collectors.toList());
        StopWatch watch = new StopWatch() ;
        watch.start("init");
        userService.saveBatch(list) ;
        watch.stop();
        log.info("=====> take time : {}", watch.getTotalTimeMillis());
    }
}
