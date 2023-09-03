package com.yicj.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.form.LoginForm;
import com.yicj.common.model.form.RegisterForm;
import com.yicj.common.model.vo.TokenVO;
import com.yicj.gateway.constants.FilterOrderConstant;
import com.yicj.gateway.remote.feign.AuthFeignClient;
import com.yicj.gateway.remote.feignrpc.AuthFeignRpc;
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
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


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


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if (path.equals(CommonConstants.GATEWAY_LOGIN_PATH)){
            // 登录操作
            String bodyContent = exchange.getAttribute(CommonConstants.CACHE_BODY_ATTRIBUTE);
            LoginForm form = JSON.parseObject(bodyContent, LoginForm.class);
            return feignRpc.login(form)
                // 输出返回给前端
                .flatMap(token -> this.printToken(exchange, token));
        }else if (path.equals(CommonConstants.GATEWAY_REGISTER_PATH)){
            String bodyContent = exchange.getAttribute(CommonConstants.CACHE_BODY_ATTRIBUTE);
            RegisterForm form = JSON.parseObject(bodyContent, RegisterForm.class);
            // 注册操作
            return feignRpc.register(form)
                // 输出返回给前端
                .flatMap(token -> this.printToken(exchange, token));
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
