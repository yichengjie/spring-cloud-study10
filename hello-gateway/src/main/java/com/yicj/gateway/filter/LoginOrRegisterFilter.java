package com.yicj.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.yicj.common.constants.CommonConstants;
import com.yicj.common.exception.AppException;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.gateway.constants.FilterOrderConstant;
import com.yicj.gateway.remote.feign.AuthFeignClient;
import com.yicj.gateway.remote.feignrpc.AuthFeignRpc;
import com.yicj.gateway.utils.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;


/**
 * 登录或则注册过滤器
 * @author yicj
 * @date 2023年09月03日 11:03
 */
@Slf4j
@Component
public class LoginOrRegisterFilter implements GlobalFilter, Ordered {

    @Autowired
    private AuthFeignClient authFeignClient ;

    @Autowired
    private AuthFeignRpc feignRpc ;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor ;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        if (path.equals(CommonConstants.GATEWAY_LOGIN_PATH)){
            // 登录操作
            log.info("[Filter] user login access ....");
            String bodyContent = exchange.getAttribute(CommonConstants.CACHE_BODY_ATTRIBUTE);
            LoginForm form = JSON.parseObject(bodyContent, LoginForm.class);
//            return feignRpc.login(form)
//                    // 输出返回给前端
//                    .flatMap(token -> this.printToken(exchange, token))
//                    .onErrorResume(error -> {
//                        AppException exception = (AppException) error ;
//                        return GatewayUtil.print2User(response, RestResponse.error(exception.getCode(), error.getMessage())) ;
//                    });

            // 这里使用subscribeOn切换独立线程池之后可以执行同步操作
            // 如果切换成Schedulers.parallel还是会继续报错，无法使用阻塞式api
            return Mono.fromSupplier(() -> authFeignClient.login(form))
                    .flatMap(rest -> {
                        log.info("[Filter] rest response : {}", rest);
                        if (!RestResponse.isDefaultSuccess(rest.getCode())) {
                            return GatewayUtil.print2User(response, RestResponse.error(rest.getCode(), rest.getMessage()));
                        }
                        return Mono.just(rest.getData());
                    })
                    .ofType(TokenVO.class)
                    .flatMap(token -> this.printToken(exchange, token))
                    .subscribeOn(Schedulers.fromExecutor(threadPoolTaskExecutor)) ;
        }else if (path.equals(CommonConstants.GATEWAY_REGISTER_PATH)){
            String bodyContent = exchange.getAttribute(CommonConstants.CACHE_BODY_ATTRIBUTE);
            RegisterForm form = JSON.parseObject(bodyContent, RegisterForm.class);
            // 注册操作
            return feignRpc.register(form)
                // 输出返回给前端
                .flatMap(token -> this.printToken(exchange, token))
                .onErrorResume(error -> {
                    AppException exception = (AppException) error ;
                    return GatewayUtil.print2User(response, RestResponse.error(exception.getCode(), error.getMessage())) ;
                });
        }
        return chain.filter(exchange) ;
    }



    private Mono<Void> printToken(ServerWebExchange exchange, TokenVO token){
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK) ;
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(token));
        Mono<DataBuffer> just = Mono.just(dataBuffer);
        return response.writeWith(just) ;
    }

    @Override
    public int getOrder() {
        return FilterOrderConstant.LOGIN_OR_REGISTER;
    }
}
