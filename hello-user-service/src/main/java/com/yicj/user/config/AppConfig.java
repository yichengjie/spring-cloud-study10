package com.yicj.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;

/**
 * @author yicj
 * @date 2023年08月27日 15:08
 */
@Configuration
public class AppConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath ;

//    @Bean
//    public WebFilter contextPathWebFilter() {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            if (request.getURI().getPath().startsWith(contextPath)) {
//                // 将request的ContentPath设置配置中的contextPath
//                ServerWebExchange newExchange = exchange.mutate()
//                        .request(request.mutate().contextPath(contextPath).build())
//                        .build();
//                return chain.filter(newExchange);
//            }
//            return chain.filter(exchange);
//        };
//    }

}
