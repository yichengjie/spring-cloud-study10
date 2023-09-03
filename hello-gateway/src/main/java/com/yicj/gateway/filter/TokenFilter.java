package com.yicj.gateway.filter;

import com.yicj.common.constants.CommonConstants;
import com.yicj.common.model.vo.RestResponse;
import com.yicj.common.model.vo.UserVO;
import com.yicj.common.utils.CommonUtil;
import com.yicj.gateway.constants.FilterOrderConstant;
import com.yicj.gateway.remote.webclient.AuthWebClient;
import com.yicj.gateway.utils.GatewayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TokenFilter implements GlobalFilter, Ordered {


    @Autowired
    private AuthWebClient authWebClient ;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        //ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst(CommonConstants.HEADER_TOKEN_NAME);
        if (StringUtils.isNotBlank(authorization)){
            return authWebClient.findByToken(authorization)
                .flatMap(res -> {
                    String code = res.getCode();
                    // 如果解析token失败，则需要报错提示
                    if (!RestResponse.isDefaultSuccess(code)){
                       return GatewayUtil.print2User(exchange.getResponse(), res)
                               .then(Mono.empty());
                    }
                    UserVO vo = res.getData();
                    log.info("根据token 解析出用户信息：{}", vo);
                    return Mono.just(vo) ;
                }).flatMap(vo -> {
                    String id = vo.getId();
                    String username = vo.getUsername();
                    String address = vo.getAddress();
                    ServerHttpRequest newRequest = exchange.getRequest().mutate()
                            .header("user_id", id)
                            .header("username", username)
                            .header("address", address)
                            .build();
                    return chain.filter(exchange.mutate().request(newRequest).build()) ;
                });
        }
        // 设置response请求头编码
        exchange.getResponse().getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Map<String,Object> errorMap = new HashMap<>() ;
        errorMap.put("code", 401) ;
        errorMap.put("message", "请登录！") ;
        errorMap.put("data", null) ;
        //如果没有传递token直接给出错误提示信息
        return GatewayUtil.print2User(exchange.getResponse(), errorMap) ;
    }



    @Override
    public int getOrder() {
        return FilterOrderConstant.TOKEN_CHECK;
    }
}
