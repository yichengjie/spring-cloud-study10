package com.yicj.gateway.remote.feignrpc;

import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.utils.CommonUtil;
import com.yicj.gateway.BaseJunit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yicj
 * @date 2023年09月03日 16:06
 */
@Slf4j
public class AuthFeignRpcTest extends BaseJunit {
    @Autowired
    private AuthFeignRpc feignRpc ;

    @Test
    public void login(){
        LoginForm form = new LoginForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        feignRpc.login(form)
            .subscribe(value -> log.info("value : {}",value));
    }

    @Test
    public void register(){
        RegisterForm form = new RegisterForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        form.setAddress("BJS");
        feignRpc.register(form)
            .subscribe(value -> log.info("value : {}",value));
    }

    @Test
    public void findByToken(){
        String token = CommonUtil.uuid() ;
        feignRpc.findByToken(token)
            .subscribe(value -> log.info("value : {}",value));
    }
}
