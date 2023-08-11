package com.yicj.mybatis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.mybatis.repository.entity.AuditLogEntity;
import com.yicj.mybatis.repository.mapper.AuditLogMapper;
import com.yicj.mybatis.service.AuditLogService;
import org.springframework.stereotype.Service;

/**
 * @author: yicj
 * @date: 2023/8/11 20:12
 */
@Service
//@DS("slave_1")
public class AuditLogServiceImpl
        extends ServiceImpl<AuditLogMapper, AuditLogEntity>
        implements AuditLogService {

}
