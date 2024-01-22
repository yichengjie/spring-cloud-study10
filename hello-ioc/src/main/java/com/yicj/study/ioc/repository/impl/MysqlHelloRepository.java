package com.yicj.study.ioc.repository.impl;

import com.yicj.study.ioc.repository.HelloRepository;

public class MysqlHelloRepository implements HelloRepository {
    @Override
    public String save(String name) {
        return "hello mysql : " + name;
    }
}
