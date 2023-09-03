package com.yicj.gateway.filter;

import com.yicj.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

/**
 * @author yicj
 * @date 2023年09月03日 12:09
 */
@Slf4j
@Component
public class CacheBodyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 校验参数
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        // 如果不是登录或则注册则直接跳过
        if (!(CommonConstants.GATEWAY_LOGIN_PATH.equals(path)
                || CommonConstants.GATEWAY_REGISTER_PATH.equals(path))){
            return chain.filter(exchange) ;
        }
        // 否则需要将body缓存
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            String bodyString = new String(bytes, StandardCharsets.UTF_8);
            exchange.getAttributes().put(CommonConstants.CACHE_BODY_ATTRIBUTE, bodyString);
            DataBufferUtils.release(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                return Mono.just(buffer);
            });
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            // String reqBody = (String)exchange.getAttributes().get("POST_BODY");
            // log.info("===> reqBody:{}", reqBody);
            ServerWebExchange mutableExchange = exchange.mutate().request(mutatedRequest).build();
            return chain.filter(mutableExchange);
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
