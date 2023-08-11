package com.yicj.mybatis.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: yicj
 * @date: 2023/8/11 10:12
 */
@Data
@TableName("t_student")
public class StudentEntity {

    @TableId(type = IdType.AUTO)
    private Integer id ;

    /**
     * 用户id
     */
    private String userId ;

    /**
     * 学号
     */
    private String studentNum ;

    /**
     * 班级编码
     */
    private String classNum ;


}
