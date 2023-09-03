package com.yicj.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Enumeration;

/**
 * @author yicj
 * @date 2023年08月27日 15:01
 */
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("/index")
    public String index(
            @RequestHeader(value = "x-token", required = false) String xtoken,
            HttpServletRequest request){
        log.info("x-token value : {}", xtoken);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            log.info("----> header :  {} : {}",headerName, headerValue);
        }
        return "user service hello index !" ;
    }

}
