package com.yicj.openfeign.controller;

import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.UserVO;
import com.yicj.openfeign.client.UserServiceHelloClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * UserServiceHelloController
 * </p>
 *
 * @author yicj
 * @since 2023年11月26日 18:46
 */
@Slf4j
@RestController
public class UserServiceHelloController {

    @Autowired
    private UserServiceHelloClient userServiceHelloClient ;

    @GetMapping("/user-svc/listAllUserString")
    public String listAllUserString(){
        String value = userServiceHelloClient.listAllUserString();
        log.info("ret value : {}", value);
        return value ;
    }

    @GetMapping("/user-svc/listAllUser")
    public RestResponse<List<UserVO>> listAllUser(){
        RestResponse<List<UserVO>> value = userServiceHelloClient.listAllUser();
        log.info("ret value : {}", value);
        return value ;
    }
}
