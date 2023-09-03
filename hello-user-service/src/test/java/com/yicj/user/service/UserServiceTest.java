package com.yicj.user.service;

import com.yicj.common.model.vo.TokenVO;
import com.yicj.user.UserServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yicj
 * @date 2023年09月03日 9:01
 */
@Slf4j
@SpringBootTest(classes = UserServiceApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService ;

    @Test
    public void register(){
        String username = "hello" ;
        String password = "123" ;
        String address = "BJS" ;
        // 注册用户
        userService.register(username, password, address)
                .subscribe(token -> log.info("token : {}", token)) ;
    }

    @Test
    public void loginSuccess(){
        String username = "hello" ;
        String password = "123" ;
        String address = "BJS" ;
        // 注册用户
        userService.register(username, password, address).subscribe() ;
        // 用户登录
        userService.login(username, password)
                .doOnError(error -> log.error("登录出错: ", error))
                .subscribe(token -> log.info("token : {}", token));

    }

    @Test
    public void loginError(){
        String username = "hello" ;
        String password = "123";
        userService.login(username, password)
                .doOnError(error -> log.error("登录出错: ", error))
                .subscribe(token -> log.info("token : {}", token));
    }


    @Test
    public void findByToken(){
        String username = "hello" ;
        String password = "123" ;
        String address = "BJS" ;
        // 注册用户
        TokenVO token = userService.register(username, password, address).block();
        log.info("login user toke : {}", token);
        // 根据token获取用户信息
        userService.findByToken(token.getToken())
                .subscribe(value -> log.info("user info : {}", value));

    }

}
