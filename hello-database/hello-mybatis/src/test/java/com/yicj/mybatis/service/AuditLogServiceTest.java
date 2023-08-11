package com.yicj.mybatis.service;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.AuditLogEntity;
import com.yicj.mybatis.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 20:40
 */
@Slf4j
//@Transactional
@SpringBootTest(classes = MybatisApplication.class)
public class AuditLogServiceTest {

    @Autowired
    private AuditLogService auditLogService ;

    @Test
    public void listAll(){
        List<AuditLogEntity> list = auditLogService.list();
        CommonUtil.printList(list, 10);
    }

    @Test
    public void listOther(){
        LambdaQueryWrapper<AuditLogEntity> wrapper = new LambdaQueryWrapper<>(new AuditLogEntity())
                .select(AuditLogEntity::getId, AuditLogEntity::getMethod, AuditLogEntity::getPath);
        List<AuditLogEntity> list = auditLogService.list(wrapper);
        CommonUtil.printList(list, 3);
    }

}
