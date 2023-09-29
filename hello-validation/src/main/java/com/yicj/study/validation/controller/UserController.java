package com.yicj.study.validation.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * @author: yicj
 * @date: 2023/9/29 13:14
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping("/createUser")
    public User createUser(@Valid User user){
        user.setDesc("this is user from demo");
        return user ;
    }
    @Data
    static class User{
        @Size(min = 3, max = 18, message = "name.length >=3 and <= 18")
        private String name ;
        private String desc ;
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception ex){
        log.error("error : ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK) ;
    }
}
