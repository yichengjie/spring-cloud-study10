package com.yicj.gateway.utils;

import com.yicj.gateway.enums.ErrorCodeEnum;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author yicj
 * @date 2023年08月26日 15:44
 */
public class ExceptionHandler {

    public static Mono<Void>  genErrResponse(ServerWebExchange exchange, ErrorCodeEnum errorCodeEnum){

        return null ;
    }
}
