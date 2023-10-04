package com.yicj.study.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author yicj
 * @date 2023年10月04日 9:23
 */
@Slf4j
@Aspect
public class HelloAspect {

    @Pointcut("execution(* com.yicj.study.aop.service..*.*(..))")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null ;
        log.info("before method execute ...");
        try {
            result = joinPoint.proceed();
            log.info("after method execute ...");
        }catch (Throwable e){
            log.info("method execute occur exception ");
            throw e ;
        }
        return result ;
    }
}
