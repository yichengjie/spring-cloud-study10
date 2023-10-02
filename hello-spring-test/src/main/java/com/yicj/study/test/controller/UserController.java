package com.yicj.study.test.controller;

import com.yicj.study.test.repository.entity.UserEntity;
import com.yicj.study.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023/9/29 20:15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService ;

    @GetMapping("/findById/{id}")
    public UserEntity findById(@PathVariable("id") Integer id){
        return userService.findById(id) ;
    }
}
