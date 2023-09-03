package com.yicj.gateway.remote.webclient;

import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.common.model.vo.UserVO;
import com.yicj.common.utils.CommonUtil;
import com.yicj.gateway.BaseJunit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yicj
 * @date 2023年09月03日 17:04
 */
@Slf4j
public class AuthWebClientTest extends BaseJunit {

    @Autowired
    private AuthWebClient authWebClient ;

    @Test
    public void login(){
        LoginForm form = new LoginForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        RestResponse<TokenVO> response = authWebClient.login(form).block();
        log.info("value : {}", response);
        //.subscribe(value -> log.info("value : {}",value));
    }

    @Test
    public void register(){
        RegisterForm form = new RegisterForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        form.setAddress("BJS");
        RestResponse<TokenVO> response = authWebClient.register(form).block();
        log.info("value : {}", response);
        //.subscribe(value -> log.info("value : {}",value));
    }


    @Test
    public void findByToken(){
        String token = "683b878f566a4dcbb79c08194ca6b9f9" ;
        RestResponse<UserVO> response = authWebClient.findByToken(token).block();
        log.info("value : {}", response);
        // .subscribe(value -> log.info("value : {}",value));
    }
}
