package com.yicj.user.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年09月03日 7:58
 */
@Data
public class UserEntity implements Serializable {

    /**
     * 主键id
     */
    private String id ;

    /**
     * 用户名
     */
    private String username ;

    /**
     * 密码
     */
    private String password ;

    /**
     * 地址
     */
    private String address ;

}
