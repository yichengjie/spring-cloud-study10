package com.yicj.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

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

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
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
