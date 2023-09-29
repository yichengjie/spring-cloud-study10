package com.yicj.study.test.service.impl;

import com.yicj.study.test.repository.entity.UserEntity;
import com.yicj.study.test.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author yicj
 * @date 2023/9/29 20:13
 */
@Service
public class UserServiceImpl implements UserService {

    private List<UserEntity> userList = new CopyOnWriteArrayList<>() ;

    @Override
    public UserEntity findById(Integer id) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        // todo ...
        return userEntity;
    }
}
