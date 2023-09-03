package com.yicj.gateway.remote.webclient;

import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.TokenVO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年09月03日 15:35
 */
@Component
public class AuthWebClient implements InitializingBean {

    @Autowired
    private WebClient.Builder builder ;
    private WebClient webClient ;


    public Mono<TokenVO> login(LoginForm form){
        return webClient.post()
                .uri(CommonConstants.AUTH_LOGIN_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(form, LoginForm.class)
                .retrieve()
                .bodyToMono(TokenVO.class) ;
    }


    public Mono<TokenVO> register(RegisterForm form){
        return webClient.post()
                .uri(CommonConstants.AUTH_REGISTER_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(form, RegisterForm.class)
                .retrieve()
                .bodyToMono(TokenVO.class) ;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        webClient =  builder
                .baseUrl("http://" + CommonConstants.AUTH_SERVICE_NAME)
                .build();
    }
}
