package com.yicj.user.config;

import com.yicj.common.exception.AppException;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

/**
 * @author yicj
 * @date 2023年09月03日 10:26
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public RestResponse<Void> handlerAppException(AppException exception){
        log.error("===> global exception handle AppException: ", exception);
        String code = exception.getCode();
        String message = exception.getMessage();
        return RestResponse.error(code, message) ;
    }

    @ExceptionHandler(Throwable.class)
    public RestResponse<Void> handlerOtherException(Throwable throwable){
        log.error("===> global exception handle OtherException: ", throwable);
        String message = Optional.ofNullable(throwable.getMessage()).orElse("服务器内部错误!");
        return RestResponse.error("500", message) ;
    }

}
