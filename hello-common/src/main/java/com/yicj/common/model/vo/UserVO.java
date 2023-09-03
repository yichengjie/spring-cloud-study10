package com.yicj.common.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年09月03日 7:46
 */
@Data
public class UserVO implements Serializable {

    /**
     * 主键id
     */
    private String id ;

    /**
     * 用户名
     */
    private String username ;

    /**
     * 地址
     */
    private String address ;
}
