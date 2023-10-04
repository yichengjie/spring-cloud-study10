package com.yicj.study.aop.service.impl;

import com.yicj.study.aop.service.HelloService;

/**
 * @author yicj
 * @date 2023年10月04日 9:21
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String name) {
        return "Hello, " + name;
    }
}
