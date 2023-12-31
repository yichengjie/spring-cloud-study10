package com.yicj.gateway.remote.feign;

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
 * @date 2023年09月03日 11:41
 */
@Slf4j
public class AuthFeignClientTest extends BaseJunit {

    @Autowired
    private AuthFeignClient feignClient ;

    @Test
    public void login(){
        LoginForm form = new LoginForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        RestResponse<TokenVO> token = feignClient.login(form);
        log.info("token : {}", token);
    }

    @Test
    public void register(){
        RegisterForm form = new RegisterForm() ;
        form.setUsername("hello");
        form.setPassword("123");
        form.setAddress("BJS");
        RestResponse<TokenVO> token = feignClient.register(form);
        log.info("token : {}", token);
    }

    @Test
    public void findByToken(){
        String token = CommonUtil.uuid();
        RestResponse<UserVO> vo = feignClient.findByToken(token);
        log.info("user vo : {}", vo);
    }
}
