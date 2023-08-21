package com.yicj.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author yicj
 * @date 2023年08月21日 13:26
 */
@Slf4j
@Component
public class CustomGatewayFilterFactory extends AbstractGatewayFilterFactory<CustomGatewayFilterFactory.Config> {

    public CustomGatewayFilterFactory(){
        super(Config.class);
    }

    //https://huaweicloud.csdn.net/638752b9dacf622b8df8ad23.html
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            Flux<DataBuffer> body = request.getBody();
//            body.subscribe(buffer -> {
//                byte [] bytes = new byte[buffer.readableByteCount()] ;
//                buffer.read(bytes) ;
//                DataBufferUtils.release(buffer) ;
//                String bodyString = new String(bytes);
//                log.info("Body String : {}", bodyString);
//            }) ;

//            AtomicReference<String> bodyRef = new AtomicReference<>();
//            body.subscribe(buffer -> {
//                CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
//                DataBufferUtils.release(buffer);
//                bodyRef.set(charBuffer.toString());
//            });
//            //获取request body
//            String bodyStr = bodyRef.get();
//            log.info(bodyStr);

            if (config.isPreLogger()) {
                log.info("CustomGatewayFilterFactory pre message is {}", config.getMessage());
            }
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("CustomGatewayFilterFactory post message is {}", config.getMessage());
                }
            }));
        };
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        private String message;
        private boolean preLogger;
        private boolean postLogger;
    }
}
