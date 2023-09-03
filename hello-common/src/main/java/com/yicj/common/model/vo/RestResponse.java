package com.yicj.common.model.vo;

import lombok.Data;

/**
 * @author yicj
 * @date 2023年09月03日 10:30
 */
@Data
public class RestResponse<T> {

    public static final String SUCCESS_CODE = "200" ;

    private String code ;

    private String message ;

    private T data ;

    public static RestResponse<Void> error(String code, String message){
        RestResponse<Void> response = new RestResponse<>() ;
        response.setCode(code);
        response.setMessage(message);
        return response ;
    }

    public static <T> RestResponse<T> success(T data){
        RestResponse<T> response = new RestResponse<>() ;
        response.setCode(SUCCESS_CODE);
        response.setData(data);
        return response ;
    }

    public static boolean isDefaultSuccess(String code){
       return SUCCESS_CODE.equals(code) ;
    }
}
