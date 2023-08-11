package com.yicj.mybatis.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yicj
 * @date: 2023/8/11 10:16
 */
@Data
public class StudentDetailDTO implements Serializable {

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

    /**
     * 用户名称
     */
    private String userName ;

    /**
     * 工作
     */
    private String job ;

    /**
     * 公司名称
     */
    private String company ;
}
