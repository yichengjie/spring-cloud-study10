package com.yicj.study.mvc.controller;

import com.yicj.study.mvc.model.form.HelloIndexForm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HttpServletRequest servletRequest;

    public HelloController(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @GetMapping("/index")
    public String index(){
        return "hello webmvc index !" ;
    }

    @PostMapping("/downstream")
    public HelloIndexForm downstream(@RequestBody HelloIndexForm form){
        servletRequest.getHeader("x-token") ;
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest() ;
        String token = request.getHeader("x-token");
        log.info("===> x token : {}", token);
        return form ;
    }

}
