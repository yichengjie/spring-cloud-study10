package com.yicj.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.List;


@Slf4j
//@Component
public class RequestParamGlobalFilter implements GlobalFilter, Ordered {

    /**
     * default HttpMessageReader
     */
    private static final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        /**
//         * save request path and serviceId into gateway context
//         */
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders headers = request.getHeaders();
//        // 处理参数
//        MediaType contentType = headers.getContentType();
//        long contentLength = headers.getContentLength();
//        if (contentLength > 0) {
//            if (MediaType.APPLICATION_JSON.equals(contentType) || MediaType.APPLICATION_JSON_UTF8.equals(contentType)) {
//                return readBody(exchange, chain);
//            }
//        }
//        return chain.filter(exchange);
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders headers = request.getHeaders();
//        HttpMethod method = request.getMethod();
//        return DataBufferUtils.join(exchange.getRequest().getBody())
//                .flatMap(dataBuffer -> {
//                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
//                    dataBuffer.read(bytes);
//                    String body = new String(bytes, Charset.forName("UTF-8"));
//                    // 此处可以对请求体进行处理
//                    exchange.getAttributes().put("cachedRequestBodyObject", body);
//                    return chain.filter(exchange.mutate().request(request.mutate()
//                            .header("Content-Type", "application/json")
//                            .body(Mono.just(DataBufferUtils.createByteBuffer(body.getBytes())))).build());
//                });
        exchange.getRequest()
                .mutate()
                .header("x-token", "hello-world") ;
        return chain.filter(exchange) ;
    }

    /**
     * ReadJsonBody
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> readBody(ServerWebExchange exchange, GatewayFilterChain chain) {
        /**
         * join the body
         */
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
                DataBufferUtils.retain(buffer);
                return Mono.just(buffer);
            });
            /**
             * repackage ServerHttpRequest
             */
            ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                @Override
                public Flux<DataBuffer> getBody() {
                    return cachedFlux;
                }
            };
            /**
             * mutate exchage with new ServerHttpRequest
             */
            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
            /**
             * read body string with default messageReaders
             */
            return ServerRequest.create(mutatedExchange, messageReaders).bodyToMono(String.class)
                    .doOnNext(objectValue -> {
                        log.debug("[GatewayContext]Read JsonBody:{}", objectValue);
                    }).then(chain.filter(mutatedExchange));
        });
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

}
