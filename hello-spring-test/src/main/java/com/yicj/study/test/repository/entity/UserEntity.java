package com.yicj.study.test.repository.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023/9/29 20:12
 */
@Data
public class UserEntity implements Serializable {

    private Integer id ;

    private String username ;

}
