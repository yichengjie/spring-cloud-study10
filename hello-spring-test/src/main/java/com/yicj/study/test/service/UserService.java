package com.yicj.study.test.service;

import com.yicj.study.test.repository.entity.UserEntity;

/**
 * @author yicj
 * @date 2023/9/29 20:10
 */
public interface UserService {

    UserEntity findById(Integer id) ;
}
