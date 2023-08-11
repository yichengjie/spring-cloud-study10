package com.yicj.mybatis.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: yicj
 * @date: 2023/8/11 10:25
 */
@Data
public class ListStudentForm implements Serializable {

    private String studentNum ;

    private String userName ;
}
