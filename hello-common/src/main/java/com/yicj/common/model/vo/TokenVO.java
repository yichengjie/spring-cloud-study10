package com.yicj.common.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年09月03日 12:33
 */
@Data
public class TokenVO implements Serializable {

    private String token ;

    public TokenVO(String token){
        this.token = token ;
    }
}
