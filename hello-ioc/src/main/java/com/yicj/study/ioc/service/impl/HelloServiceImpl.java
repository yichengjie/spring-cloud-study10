package com.yicj.study.ioc.service.impl;

import com.yicj.study.ioc.repository.HelloRepository;
import com.yicj.study.ioc.service.HelloService;
import lombok.Data;

@Data
public class HelloServiceImpl implements HelloService {

    private HelloRepository repository ;

    @Override
    public String hello(String name) {
        return repository.save(name);
    }
}
