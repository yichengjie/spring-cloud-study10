package com.yicj.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.common.constants.CommonConstants;
import com.yicj.gateway.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenFilter implements GlobalFilter, Ordered {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();
        String authorization = headers.getFirst(CommonConstants.HEADER_TOKEN_NAME);
        if (StringUtils.isNotBlank(authorization)){
            return chain.filter(exchange).contextWrite(context -> context.put("trace_id", CommonUtil.uuid())) ;
        }
        // 设置response请求头编码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //如果没有传递token直接给出错误提示信息
        return response.writeWith(Mono.create(monoSink -> {
            Map<String,Object> retMap = new HashMap<>() ;
            retMap.put("code", 401) ;
            retMap.put("message", "请登录！") ;
            retMap.put("data", null) ;
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(retMap);
                DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                monoSink.success(dataBuffer);
            }catch (JsonProcessingException e){
                monoSink.error(e);
            }
        })) ;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
