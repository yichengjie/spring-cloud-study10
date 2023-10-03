package com.yicj.webflux.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年10月03日 12:00
 */
@Slf4j
@Component
public class WorldFilter implements WebFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("world filter do filter logic ...");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
