package com.yicj.sentinel.service.impl;

import com.yicj.sentinel.service.HelloService;


public class HelloServiceImpl implements HelloService {

    private String address ;

    @Override
    public String hello(String name) {
        return "hello, " + name;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
