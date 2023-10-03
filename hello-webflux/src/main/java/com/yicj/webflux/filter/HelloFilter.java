package com.yicj.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年10月03日 10:18
 */
@Slf4j
@Component
public class HelloFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("hello filter do filter logic ...");
        return chain.filter(exchange) ;
    }
}
