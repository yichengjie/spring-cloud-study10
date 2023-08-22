package com.yicj.study.mvc.support;

import org.springframework.beans.factory.ObjectFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ObjectFactoryDelegatingInvocationHandler implements InvocationHandler, Serializable {

    private final ObjectFactory<?> objectFactory;

    public ObjectFactoryDelegatingInvocationHandler(ObjectFactory<?> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return switch (method.getName()) {
            case "equals" -> (proxy == args[0]); // Only consider equal when proxies are identical.
            case "hashCode" -> System.identityHashCode(proxy); // Use hashCode of proxy.
            case "toString" -> this.objectFactory.toString();
            default -> {
                try {
                    yield method.invoke(this.objectFactory.getObject(), args);
                }
                catch (InvocationTargetException ex) {
                    throw ex.getTargetException();
                }
            }
        };
    }
}
