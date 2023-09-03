package com.yicj.common.exception;

import lombok.Getter;

/**
 * @author yicj
 * @date 2023年09月03日 10:27
 */
@Getter
public class AppException extends RuntimeException {

    private String code ;

    public AppException(String code, String message){
        super(message);
        this.code = code ;
    }

}
