package com.yicj.gateway.remote.webclient;

import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.common.model.vo.UserVO;
import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
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

    public Mono<RestResponse<TokenVO>> login(LoginForm form){
        return webClient.post()
                .uri(CommonConstants.AUTH_LOGIN_PATH)
                //.accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponse<TokenVO>>(){}) ;
    }


    public Mono<RestResponse<TokenVO>> register(RegisterForm form){
        return webClient.post()
                .uri(CommonConstants.AUTH_REGISTER_PATH)
                //.accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(form)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponse<TokenVO>>(){}) ;
    }


    public Mono<RestResponse<UserVO>> findByToken(String token){
        return webClient.get()
                .uri(CommonConstants.AUTH_FIND_BY_TOKEN_PATH)
                .header(CommonConstants.HEADER_TOKEN_NAME, token)
                .accept(MediaType.ALL)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<RestResponse<UserVO>>(){}) ;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        webClient =  builder
                .baseUrl("http://" + CommonConstants.AUTH_SERVICE_NAME)
                .build();
    }
}
