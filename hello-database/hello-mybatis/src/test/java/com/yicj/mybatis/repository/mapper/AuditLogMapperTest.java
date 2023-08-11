package com.yicj.mybatis.repository.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yicj.mybatis.MybatisApplication;
import com.yicj.mybatis.repository.entity.AuditLogEntity;
import com.yicj.mybatis.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yicj
 * @date: 2023/8/11 20:14
 */
@Slf4j
@SpringBootTest(classes = MybatisApplication.class)
public class AuditLogMapperTest {

    @Autowired
    private AuditLogMapper auditLogMapper ;

    @Test
    public void listAll(){
        List<AuditLogEntity> list = auditLogMapper.selectList(new QueryWrapper<>());
        CommonUtil.printList(list, 10);
    }
}
