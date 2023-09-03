package com.yicj.user.service;

import com.yicj.common.model.vo.UserVO;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年09月03日 7:43
 */
public interface UserService {

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    Mono<String> login(String username, String password) ;

    /**
     * 注册
     * @param username 用户名
     * @param password 密码
     * @return token
     */
    Mono<String> register(String username, String password, String address) ;


    Mono<UserVO> findByToken(String token) ;

}
