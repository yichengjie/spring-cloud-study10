package com.yicj.study.mvc.service.impl;

import com.yicj.study.mvc.service.HelloService;

/**
 * @author yicj
 * @date 2023/10/24 22:19
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "hello, " + name;
    }
}
