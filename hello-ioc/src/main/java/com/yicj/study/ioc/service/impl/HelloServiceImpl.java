package com.yicj.study.ioc.service.impl;

import com.yicj.study.ioc.repository.HelloRepository;
import com.yicj.study.ioc.service.HelloService;
import lombok.Data;

@Data
public class HelloServiceImpl implements HelloService {

    private String name ;

    private HelloRepository repository ;

    public HelloServiceImpl(String name){
        this.name = name ;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String hello(String name) {
        return repository.save(name);
    }
}
