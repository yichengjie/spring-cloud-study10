package com.yicj.gateway.remote.feignrpc;

import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.common.model.vo.UserVO;
import com.yicj.gateway.remote.feign.AuthFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.concurrent.CompletableFuture;

/**
 * @author yicj
 * @date 2023年09月03日 15:55
 */
@Component
public class AuthFeignRpc {

    @Autowired
    private AuthFeignClient authFeignClient ;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor ;


    public Mono<TokenVO> login(LoginForm form){
        CompletableFuture<TokenVO> future = CompletableFuture.supplyAsync(
                () -> authFeignClient.login(form), threadPoolTaskExecutor);
        return Mono.fromFuture(future) ;
    }


    public Mono<TokenVO> register(RegisterForm form){
        CompletableFuture<TokenVO> future = CompletableFuture.supplyAsync(
                () -> authFeignClient.register(form), threadPoolTaskExecutor);
        return Mono.fromFuture(future) ;
    }

    public Mono<UserVO> findByToken(String token){
        CompletableFuture<UserVO> future = CompletableFuture.supplyAsync(
                () -> authFeignClient.findByToken(token), threadPoolTaskExecutor);
        return Mono.fromFuture(future) ;
    }

}
