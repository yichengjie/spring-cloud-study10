package com.yicj.mybatis.plugin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.model.dto.StudentDetailDTO;
import com.yicj.mybatis.model.form.ListStudentForm;
import com.yicj.mybatis.repository.entity.StudentEntity;
import com.yicj.mybatis.repository.mapper.StudentMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.stream.IntStream;

/**
 * @author: yicj
 * @date: 2023/8/11 10:31
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class PaginationInnerInterceptorTest {

    @Autowired
    private StudentMapper studentMapper ;

    /**
     * 正常分页查询
     */
    @Test
    void pageStudentDetail(){
        ListStudentForm form = new ListStudentForm() ;
        //form.setStudentNum("1001");
        form.setUserName("王五");
        IPage<StudentEntity> pageParam = new Page<>(1,3) ;
        IPage<StudentDetailDTO> page = studentMapper.pageStudentDetail(pageParam, form);
        log.info("list size : {}", page.getRecords());
        page.getRecords().forEach(item -> log.info("item : {}", item));
    }

    /**
     * 不分页查询，则需要传入pageSize小于0即可
     */
    @Test
    void pageStudentDetailNoPage(){
        ListStudentForm form = new ListStudentForm() ;
        //form.setStudentNum("1001");
        //
        IPage<StudentEntity> pageParam = new Page<>(1,-1) ;
        IPage<StudentDetailDTO> page = studentMapper.pageStudentDetail(pageParam, form);
        log.info("list size : {}", page.getRecords().size());
        page.getRecords().forEach(item -> log.info("item : {}", item));
    }

    /**
     * 不执行count语句
     */
    @Test
    void pageStudentDetailNotCount(){
        ListStudentForm form = new ListStudentForm() ;
        form.setUserName("王五");
        Page<StudentEntity> pageParam = new Page<>(1,3) ;
        pageParam.setSearchCount(false) ;
        pageParam.setMaxLimit(2L);
        IPage<StudentDetailDTO> page = studentMapper.pageStudentDetail(pageParam, form);
        log.info("list size : {}", page.getRecords().size());
        page.getRecords().forEach(item -> log.info("item : {}", item));
    }

    /**
     * 指定分页count的sql语句
     */
    @Test
    void pageStudentDetailAssignCountId(){
        ListStudentForm form = new ListStudentForm() ;
        //form.setStudentNum("1001");
        form.setUserName("王五");
        Page<StudentEntity> pageParam = new Page<>(1,3) ;
        pageParam.setCountId("countStudentDetail");   // 指定countId
        IPage<StudentDetailDTO> page = studentMapper.pageStudentDetail(pageParam, form);
        //log.info("list size : {}", list.size());
        log.info("page count: {}", page.getPages());
        page.getRecords().forEach(item -> log.info("item : {}", item));
    }


    /**
     * 简单初始化部分数据
     */
    @Test
    void batchInsert(){
        IntStream.range(2, 22)
                .mapToObj(item ->{
                    StudentEntity entity = new StudentEntity() ;
                    entity.setId(item);
                    entity.setStudentNum("100"+ item);
                    entity.setUserId(item + "");
                    entity.setClassNum("1");
                    return entity ;
                }).forEach(item -> studentMapper.insert(item));
    }

}
