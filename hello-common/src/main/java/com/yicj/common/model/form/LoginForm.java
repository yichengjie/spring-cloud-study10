package com.yicj.common.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录表单数据
 * @author yicj
 * @date 2023年09月03日 7:39
 */
@Data
public class LoginForm implements Serializable {

    private String username ;

    private String password ;

}
