package com.yicj.nacos.client.remote.client;

import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月27日 18:55
 */
public interface UserHelloClient {

    @GetExchange(value = "/hello/index", accept = "application/json;charset=utf-8")
    String index() ;
}
