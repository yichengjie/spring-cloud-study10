package com.yicj.mybatis.repository.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.mybatis.repository.entity.AuditLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: yicj
 * @date: 2023/8/11 20:11
 */
@Mapper
public interface AuditLogMapper extends BaseMapper<AuditLogEntity> {

}
