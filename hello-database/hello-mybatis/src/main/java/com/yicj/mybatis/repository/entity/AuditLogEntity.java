package com.yicj.mybatis.repository.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author: yicj
 * @date: 2023/8/11 20:08
 */
@Data
@TableName("audit_log")
public class AuditLogEntity {

    private Integer id ;

    private String method ;

    private String path ;

    private String status ;

    private String username ;

    private LocalDateTime createTime ;

    private LocalDateTime modifyTime ;
}
