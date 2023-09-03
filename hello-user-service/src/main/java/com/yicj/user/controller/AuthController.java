package com.yicj.user.controller;

import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.common.model.vo.UserVO;
import com.yicj.common.utils.CommonUtil;
import com.yicj.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年09月03日 7:40
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService ;

    @PostMapping("/login")
    public Mono<RestResponse<TokenVO>> login(@RequestBody LoginForm form){
        log.info("[Controller] user login , username: {}, password: {}", form.getUsername(), form.getPassword());
        String username = form.getUsername();
        String password = form.getPassword();
        CommonUtil.sleepQuiet(1000);
        return userService.login(username, password)
            .map(RestResponse::success);
    }

    @PostMapping("/register")
    public Mono<RestResponse<TokenVO>> register(@RequestBody RegisterForm form){
        log.info("[Controller] user register , username: {}, password: {}", form.getUsername(), form.getPassword());
        String username = form.getUsername();
        String password = form.getPassword();
        String address = form.getAddress();
        CommonUtil.sleepQuiet(1000);
        return userService.register(username, password, address)
            .map(RestResponse::success);
    }

    @GetMapping("/findByToken")
    public Mono<RestResponse<UserVO>> findByToken(@RequestHeader("token") String token){
        log.info("[Controller] findByToken , token {}", token);
        CommonUtil.sleepQuiet(1000);
        return userService.findByToken(token)
            .map(RestResponse::success);
    }

}
