package com.yicj.common.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册表单数据
 * @author yicj
 * @date 2023年09月03日 8:13
 */
@Data
public class RegisterForm implements Serializable {

    private String username ;

    private String password ;

    private String address ;

}
