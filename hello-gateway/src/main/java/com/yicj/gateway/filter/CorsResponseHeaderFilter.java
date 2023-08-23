package com.yicj.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

// https://blog.csdn.net/dydyswr/article/details/126734883?spm=1001.2014.3001.5501
// https://blog.csdn.net/g3230863/article/details/119637318
public class CorsResponseHeaderFilter implements GlobalFilter, Ordered {

    private boolean enabled;

    private boolean cookieEnabled;

    @Override
    public int getOrder() {
        // 指定此过滤器位于NettyWriteResponseFilter之后
        // 即待处理完响应体后接着处理响应头
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> filterHeaders= Arrays.asList(
                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,
                HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS
        );
        return chain.filter(exchange)
                .then(Mono.defer(()->{
                    System.out.println("2222222222");
                    //重新执行这个filter后的所有filter
                    return chain.filter(exchange);
                }));
    }

}
