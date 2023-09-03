package com.yicj.gateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年09月02日 22:51
 */
public class GatewayUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Mono<Void> print2User(ServerHttpResponse response, Object obj){
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.create(monoSink -> {
            try {
                byte[] bytes = objectMapper.writeValueAsBytes(obj);
                DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                monoSink.success(dataBuffer);
            }catch (JsonProcessingException e){
                monoSink.error(e);
            }
        })) ;
    }
}
