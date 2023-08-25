package com.yicj.study.mvc.controller;

import com.yicj.study.mvc.model.form.HelloIndexForm;
import com.yicj.study.mvc.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {

    private final HttpServletRequest servletRequest;

    public HelloController(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    @GetMapping("/index")
    @ResponseBody
    public String index(){
        return "hello webmvc index !" ;
    }

    @ResponseBody
    @PostMapping("/downstream")
    public HelloIndexForm downstream(@RequestBody HelloIndexForm form){
        servletRequest.getHeader("x-token") ;
        //HttpServletRequest request = CommonUtils.getHttpServletRequest() ;
        HttpServletRequest proxy = CommonUtils.getHttpServletRequestProxy(this.getClass().getClassLoader());
        String token = proxy.getHeader("x-token");
        log.info("===> x token : {}", token);
        return form ;
    }

    @GetMapping("/async/hello")
    @ResponseBody
    public DeferredResult<String> asyncHello(){
        DeferredResult<String> result = new DeferredResult<>() ;
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result.setResult("hello async result !") ;
        }) ;
        return result ;
    }

    class HttpServletRequestInvocationHandler implements InvocationHandler{
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            HttpServletRequest request = CommonUtils.getHttpServletRequest() ;
            return method.invoke(request, args) ;
            //return InvocationHandler.invokeDefault(request, method, args);
        }
    }

}
