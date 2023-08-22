package com.yicj.study.mvc.utils;

import com.yicj.study.mvc.support.ObjectFactoryDelegatingInvocationHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Proxy;

public class CommonUtils {

    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest() ;
    }

    public static HttpServletRequest getHttpServletRequestProxy(ClassLoader loader){
        ObjectFactory<HttpServletRequest> objectFactory = CommonUtils::getHttpServletRequest;
        return (HttpServletRequest) Proxy.newProxyInstance(loader,
                new Class[]{HttpServletRequest.class},
                new ObjectFactoryDelegatingInvocationHandler(objectFactory)) ;
    }
}
