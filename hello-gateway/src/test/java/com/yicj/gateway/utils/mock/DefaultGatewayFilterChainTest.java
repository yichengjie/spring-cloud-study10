package com.yicj.gateway.utils.mock;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

/**
 * @author yicj
 * @date 2023年08月23日 17:17
 */
@Slf4j
public class DefaultGatewayFilterChainTest {

    @Test
    public void filter(){
        // 1. 构建filter链条
        List<GatewayFilter> filters = Arrays.asList(
            new MockGatewayFilter()
        );
        DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(filters);
        // 2. 构建 ServerWebExchange
        MockServerHttpRequest request = MockServerHttpRequest
                .get("https://www.baidu.com")
                .build();
        ServerWebExchange exchange = MockServerWebExchange.from(request) ;
        // 3.  指定filter方法
        chain.filter(exchange)
                .subscribe() ;
    }

    private static class MockGatewayFilter implements GatewayFilter{
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return chain.filter(exchange);
        }
    }

    private static class DefaultGatewayFilterChain implements GatewayFilterChain {

        private final int index;

        private final List<GatewayFilter> filters;

        DefaultGatewayFilterChain(List<GatewayFilter> filters) {
            this.filters = filters;
            this.index = 0;
        }

        private DefaultGatewayFilterChain(DefaultGatewayFilterChain parent, int index) {
            this.filters = parent.getFilters();
            this.index = index;
        }

        public List<GatewayFilter> getFilters() {
            return filters;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange) {
            return Mono.defer(() -> {
                if (this.index < filters.size()) {
                    log.info("-----------> index: {}", this.index);
                    GatewayFilter filter = filters.get(this.index);
                    DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(this, this.index + 1);
                    return filter.filter(exchange, chain);
                }
                else {
                    return Mono.empty(); // complete
                }
            });
//            if (this.index < filters.size()) {
//                log.info("-----------> index: {}", this.index);
//                GatewayFilter filter = filters.get(this.index);
//                DefaultGatewayFilterChain chain = new DefaultGatewayFilterChain(this, this.index + 1);
//                return filter.filter(exchange, chain);
//            }
//            else {
//                return Mono.empty(); // complete
//            }
        }
    }


    private static class GatewayFilterAdapter implements GatewayFilter {

        private final GlobalFilter delegate;

        GatewayFilterAdapter(GlobalFilter delegate) {
            this.delegate = delegate;
        }

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            return this.delegate.filter(exchange, chain);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("GatewayFilterAdapter{");
            sb.append("delegate=").append(delegate);
            sb.append('}');
            return sb.toString();
        }

    }
}
